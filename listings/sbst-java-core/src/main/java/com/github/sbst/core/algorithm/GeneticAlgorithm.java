package com.github.sbst.core.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import com.github.sbst.ClassCompiler;
import com.github.sbst.JUnit5TestRunner;
import com.github.sbst.TestGenerator;
import com.github.sbst.core.BlueprintMaterializer;
import com.github.sbst.core.TestSuiteBlueprintsGenerator;
import com.github.sbst.core.ValuePlaceholderMaterializer;
import com.github.sbst.core.blueprint.TestSuiteBlueprint;
import com.github.sbst.core.blueprint.materialized.LocalVariableDeclarationWithAssignMB;
import com.github.sbst.core.blueprint.materialized.TestMethodMB;
import com.github.sbst.core.blueprint.materialized.TestSuiteMB;
import com.github.sbst.core.info.ClassInfo;

public class GeneticAlgorithm {
    final ValuePlaceholderMaterializer valuePlaceholderMaterializer = new ValuePlaceholderMaterializer();
    private final GeneticAlgorithmConfig geneticAlgorithmConfig;
    private final ClassCompiler compiler;
    private final JUnit5TestRunner testRunner;
    private final TestGenerator testGenerator;
    private final FitnessFunction fitnessFunction;
    private final TestSuiteBlueprintsGenerator testSuiteBlueprintsGenerator = new TestSuiteBlueprintsGenerator();
    private final Random rnd = new Random();
    private final AtomicLong chromosomeIdSeq = new AtomicLong();

    public GeneticAlgorithm(GeneticAlgorithmConfig geneticAlgorithmConfig, ClassCompiler compiler, JUnit5TestRunner testRunner, TestGenerator testGenerator, FitnessFunction fitnessFunction) {
        this.geneticAlgorithmConfig = geneticAlgorithmConfig;
        this.compiler = compiler;
        this.testRunner = testRunner;
        this.testGenerator = testGenerator;
        this.fitnessFunction = fitnessFunction;
    }

    public AlgorithmOutput run(ClassInfo classInfo) {
        List<TestSuiteBlueprint> testSuiteBlueprints = testSuiteBlueprintsGenerator.generateAllPossibleBlueprintsFrom(classInfo);

        List<Chromosome> initialChromosomes = generateChromosomesFrom(classInfo, testSuiteBlueprints);

        final var population = new Population(initialChromosomes, fitnessFunction);
        // Fitness
        population.calculateFitnessForEachChromosome();

        int maxIterations = 10000;
        for (int i = 0; i < maxIterations; i++) {
            // Selection
            final var firstFittedChromosome = population.getFirstFittedChromosome();
            final var secondFittedChromosome = population.getSecondFittedChromosome();

            // Crossover
            final var crossover1 = crossover(firstFittedChromosome, secondFittedChromosome);
            final var crossover2 = crossover(firstFittedChromosome, secondFittedChromosome);

            // Mutation
            final var matationCandidate = choose(crossover1, crossover2);
            final var mutated = mutate(matationCandidate);

            // Replace weak chromosomes with new ones
            population.replaceLatestsFittedChromosome(
                    matationCandidate == crossover1 ? crossover2 : crossover1,
                    mutated
            );

            // Fitness
            population.calculateFitnessForEachChromosome();

            // Complete check
            if (population.isTherePerfectFit()) {
                return getWinner(population);
            }
        }

        return getWinner(population);
    }

    private AlgorithmOutput getWinner(Population population) {
        final var winner = population.getFirstFittedChromosome();
        return new AlgorithmOutput(
                Map.of(winner.getTestSuiteMB().getTestSuiteName(), population.getFirstFittedSourceCode()),
                Map.of(winner.getTestSuiteMB().getTestSuiteName(), winner.getTestSuiteMB().getTestMethodBlueprints().size()),
                winner.getTestSuiteMB(), population.getFirstFittedScore()
        );
    }

    private List<Chromosome> generateChromosomesFrom(ClassInfo classInfo, List<TestSuiteBlueprint> testSuiteBlueprints) {
        List<Chromosome> allChromosomes = new ArrayList<>();
        for (int i = 1; i < 20; i++) {
            BlueprintMaterializer blueprintMaterializer = new BlueprintMaterializer(new ValuePlaceholderMaterializer(), i);
            final var initialChromosomes = testSuiteBlueprints.stream()
                                                              .map(blueprintMaterializer::materialize)
                                                              .map(mb -> new Chromosome(chromosomeIdSeq.incrementAndGet(), mb, classInfo))
                                                              .collect(Collectors.toList());
            allChromosomes.addAll(initialChromosomes);
        }
        return allChromosomes;
    }

    private Chromosome mutate(Chromosome chromosome) {
        //fixme
        final var mutationStrategy = Math.abs(rnd.nextInt()) % 3;
        switch (mutationStrategy) {
            case 0:
                // do nothing
                return chromosome;
            case 1:
                return mutateRandomParameters(chromosome);
            default:
                return addNewTestMethod(chromosome);
        }
    }

    private Chromosome mutateRandomParameters(Chromosome chromosome) {
        final ArrayList<TestMethodMB> testMethodBlueprints = new ArrayList<>(chromosome.getTestSuiteMB().getTestMethodBlueprints());
        final TestMethodMB peakedMethod = testMethodBlueprints.get(Math.abs(rnd.nextInt()) % testMethodBlueprints.size());
        final ArrayList<LocalVariableDeclarationWithAssignMB> givenParameters = new ArrayList<>(peakedMethod.getGivenParameters());
        final var peakedParameter = givenParameters.get(Math.abs(rnd.nextInt()) % givenParameters.size());

        givenParameters.removeIf(peakedParameter::equals);
        testMethodBlueprints.removeIf(peakedMethod::equals);

        givenParameters.add(LocalVariableDeclarationWithAssignMB.materializedBuilder()
                                                                .from(peakedParameter)
                                                                .materializedValuePlaceholder(valuePlaceholderMaterializer.materialize(peakedParameter.getValuePlaceholder())) // fixme
                                                                .build());

        testMethodBlueprints.add(TestMethodMB.materializedBuilder()
                                             .methodName(peakedMethod.getMethodName())
                                             .givenParameters(givenParameters)
                                             .methodCallBlueprint(peakedMethod.getMethodCallBlueprint())
                                             .expectedResult(peakedMethod.getExpectedResult())
                                             .assertBlueprint(peakedMethod.getAssertBlueprint())
                                             .build());

        final var mutatedTS = TestSuiteMB.materializedBuilder()
                                         .testSuiteName(chromosome.getTestSuiteMB().getTestSuiteName())
                                         .fieldDeclarationWithAssignBlueprints(chromosome.getTestSuiteMB().getFieldDeclarationWithAssignBlueprints())
                                         .beforeEachMethodBlueprint(chromosome.getTestSuiteMB().getBeforeEachMethodBlueprint())
                                         .testMethodBlueprints(testMethodBlueprints)
                                         .build();

        return new Chromosome(chromosomeIdSeq.incrementAndGet(), mutatedTS, chromosome.getClassInfo());
    }

    private Chromosome addNewTestMethod(Chromosome chromosome) {
        final ArrayList<TestMethodMB> testMethodBlueprints = new ArrayList<>(chromosome.getTestSuiteMB().getTestMethodBlueprints());
        final TestMethodMB peakedMethod = testMethodBlueprints.get(Math.abs(rnd.nextInt()) % testMethodBlueprints.size());
        final ArrayList<LocalVariableDeclarationWithAssignMB> givenParameters = new ArrayList<>(peakedMethod.getGivenParameters());
        final var peakedParameter = givenParameters.get(Math.abs(rnd.nextInt()) % givenParameters.size());

        givenParameters.removeIf(peakedParameter::equals);

        givenParameters.add(LocalVariableDeclarationWithAssignMB.materializedBuilder()
                                                                .from(peakedParameter)
                                                                .materializedValuePlaceholder(valuePlaceholderMaterializer.materialize(peakedParameter.getValuePlaceholder())) // fixme
                                                                .build());

        testMethodBlueprints.add(TestMethodMB.materializedBuilder()
                                             .methodName(peakedMethod.getMethodName() + rnd.nextInt(100000)) //fixme
                                             .givenParameters(givenParameters)
                                             .methodCallBlueprint(peakedMethod.getMethodCallBlueprint())
                                             .expectedResult(peakedMethod.getExpectedResult())
                                             .assertBlueprint(peakedMethod.getAssertBlueprint())
                                             .build());

        final var mutatedTS = TestSuiteMB.materializedBuilder()
                                         .testSuiteName(chromosome.getTestSuiteMB().getTestSuiteName())
                                         .fieldDeclarationWithAssignBlueprints(chromosome.getTestSuiteMB().getFieldDeclarationWithAssignBlueprints())
                                         .beforeEachMethodBlueprint(chromosome.getTestSuiteMB().getBeforeEachMethodBlueprint())
                                         .testMethodBlueprints(testMethodBlueprints)
                                         .build();

        return new Chromosome(chromosomeIdSeq.incrementAndGet(), mutatedTS, chromosome.getClassInfo());
    }

    private Chromosome crossover(Chromosome firstFittedChromosome, Chromosome secondFittedChromosome) {
        final var testSuit = TestSuiteMB.materializedBuilder()
                                        .testSuiteName(firstFittedChromosome.getTestSuiteMB().getTestSuiteName())
                                        .fieldDeclarationWithAssignBlueprints(choose(
                                                firstFittedChromosome.getTestSuiteMB().getFieldDeclarationWithAssignBlueprints(),
                                                secondFittedChromosome.getTestSuiteMB().getFieldDeclarationWithAssignBlueprints()
                                        ))
                                        .beforeEachMethodBlueprint(choose(
                                                firstFittedChromosome.getTestSuiteMB().getBeforeEachMethodBlueprint(),
                                                secondFittedChromosome.getTestSuiteMB().getBeforeEachMethodBlueprint()
                                        ))
                                        .testMethodBlueprints(mixTestMethods(
                                                firstFittedChromosome.getTestSuiteMB().getTestMethodBlueprints(),
                                                secondFittedChromosome.getTestSuiteMB().getTestMethodBlueprints()
                                        )).build();
        return new Chromosome(chromosomeIdSeq.incrementAndGet(), testSuit, firstFittedChromosome.getClassInfo());

    }

    private List<? extends TestMethodMB> mixTestMethods(List<? extends TestMethodMB> a, List<? extends TestMethodMB> b) {
        final ArrayList<TestMethodMB> resultTests = new ArrayList<TestMethodMB>();
        if (a.size() > b.size()) { //todo give names in more elegant way
            for (int i = 0; i < b.size(); i++) {
                final TestMethodMB a1 = a.get(i);
                final TestMethodMB buildA = TestMethodMB.materializedBuilder().from(a1).methodName(a1.getMethodName() + chromosomeIdSeq.incrementAndGet()).build();
                final TestMethodMB b1 = b.get(i);
                final TestMethodMB buildB = TestMethodMB.materializedBuilder().from(b1).methodName(b1.getMethodName() + chromosomeIdSeq.incrementAndGet()).build();
                resultTests.add(choose(buildA, buildB));
            }
        } else {
            for (int i = 0; i < a.size(); i++) {
                final TestMethodMB a1 = a.get(i);
                final TestMethodMB buildA = TestMethodMB.materializedBuilder().from(a1).methodName(a1.getMethodName() + chromosomeIdSeq.incrementAndGet()).build();
                final TestMethodMB b1 = b.get(i);
                final TestMethodMB buildB = TestMethodMB.materializedBuilder().from(b1).methodName(b1.getMethodName() + chromosomeIdSeq.incrementAndGet()).build();
                resultTests.add(choose(buildA, buildB));
            }
        }

        return resultTests;

    }

    private <T> T choose(T a, T b) {
        return Math.abs(rnd.nextInt()) % 2 == 0 ? a : b; //todo: choosing factor?
    }

}
