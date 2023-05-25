package sequencial;

import java.util.Arrays;

public class AGApp {

    public static void main(String[] args) {

       Population population=new Population();
       population.initialaizePopulation();
       population.calculateIndFintess();
       population.sortPopulation();
       int it=0;
       System.out.println("Chromosome :"+Arrays.toString(population.getFitnessIndivd().getGenes())+" fitness :"+population.getFitnessIndivd().getFitness());

        while (it<GAUtils.MAX_IT && population.getFitnessIndivd().getFitness()<GAUtils.CHROMOSOME_SIZE){
            population.selection();
            population.crossover();
            population.mutation();
            population.calculateIndFintess();
            population.sortPopulation();
            System.out.println("It :"+it+"Chromosome :"+Arrays.toString(population.getFitnessIndivd().getGenes())+" fitness :"+population.getFitnessIndivd().getFitness());

         it++;
        }

    }
}
