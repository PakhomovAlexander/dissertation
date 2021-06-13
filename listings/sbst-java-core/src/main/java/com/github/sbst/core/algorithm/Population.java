package com.github.sbst.core.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Population {
    private final FitnessFunction fitnessFunction;
    private List<Chromosome> chromosomes;
    private Map<Chromosome, FitnessOutput> fittedChromosomes;
    private boolean firstPopulation = true;

    public Population(List<Chromosome> chromosomes, FitnessFunction fitnessFunction) {
        this.chromosomes = new ArrayList<>(chromosomes);
        this.fitnessFunction = fitnessFunction;
    }

    public boolean isTherePerfectFit() {
        final var max = fittedChromosomes.values().stream().map(FitnessOutput::getFitScore).max(Double::compareTo);
        return max.get() == 1.0;
    }

    public void calculateFitnessForEachChromosome() {
        fittedChromosomes = chromosomes.stream().collect(Collectors.toMap(
                chromosome -> chromosome,
                chromosome -> {
                    if (firstPopulation || !fittedChromosomes.containsKey(chromosome)) {
                        return fitnessFunction.apply(chromosome.getTestSuiteMB(), chromosome.getClassInfo());
                    } else {
                        return fittedChromosomes.get(chromosome);
                    }
                }
        ));
    }

    public Chromosome getFirstFittedChromosome() {
        return fittedChromosomes.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
    }

    public Double getFirstFittedScore() {
        return fittedChromosomes.entrySet().stream().max(Map.Entry.comparingByValue()).get().getValue().getFitScore();
    }

    public String getFirstFittedSourceCode() {
        return fittedChromosomes.entrySet().stream().max(Map.Entry.comparingByValue()).get().getValue().getSourceCode();
    }

    public Chromosome getSecondFittedChromosome() {
        final var firstFittedChromosome = getFirstFittedChromosome();
        return fittedChromosomes.entrySet().stream()
                                .filter(chromosome -> !chromosome.getKey().equals(firstFittedChromosome))
                                .max(Map.Entry.comparingByValue()).get().getKey();
    }

    public Chromosome getByIndex(int i) {
        return chromosomes.get(i);
    }

    public int getSize() {
        return chromosomes.size();
    }

    public void replaceLatestFittedChromosome(Chromosome newChromosome) {
        final var latestFittedChromosome = fittedChromosomes.entrySet().stream().min(Map.Entry.comparingByValue()).get().getKey();
        chromosomes.removeIf(chromosome -> chromosome.equals(latestFittedChromosome));
        chromosomes.add(newChromosome);
    }

    public void replaceLatestsFittedChromosome(Chromosome newChromosome1, Chromosome newChromosome2) {
        final var latestFittedChromosome = fittedChromosomes.entrySet().stream().min(Map.Entry.comparingByValue()).get().getKey();
        final var latestFittedChromosome2 = fittedChromosomes.entrySet().stream().filter(e -> !e.getKey().equals(latestFittedChromosome)).min(Map.Entry.comparingByValue()).get().getKey();

        chromosomes.removeIf(latestFittedChromosome::equals);
        chromosomes.removeIf(latestFittedChromosome2::equals);

        fittedChromosomes.remove(latestFittedChromosome);
        fittedChromosomes.remove(latestFittedChromosome2);

        if (!chromosomes.contains(newChromosome1)) {
            chromosomes.add(newChromosome1);
        }
        if (!chromosomes.contains(newChromosome2)) {
            chromosomes.add(newChromosome2);
        }
    }
}
