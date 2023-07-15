//package hammurabi;

import java.util.Random;
import java.util.Scanner;



public class Hammurabi {
    static Random random = new Random();
    Scanner scanner = new Scanner(System.in);

    int people = 100;
    int grainBushels = 2800;
    int landAcres = 1000;
    int landValue = 19;     //bushels/acre

    public static void main(String[] args) {
        new Hammurabi().playGame();
    }

    void playGame() {


        //add loop while game is active
//        printYearlyStatus();
//        getPlayerInput();
//        process();

        // declare local variables here: grain, population, etc.
        // statements go after the declations
    }

    private void process() { // update values based on player input
    }

    private void getPlayerInput() { //player input for 5(?) options

    }

    public void printYearlyStatus(){ //start each round with an update
        /*
        O great Hammurabi!
        You are in year 1 of your ten year rule.
        In the previous year 0 people starved to death.
        In the previous year 5 people entered the kingdom.
        The population is now 100.
        We harvested 3000 bushels at 3 bushels per acre.
        Rats destroyed 200 bushels, leaving 2800 bushels in storage.
        The city owns 1000 acres of land.
        Land is currently worth 19 bushels per acre.
         */

    }


    int askHowManyAcresToBuy(int price, int bushels){
        return 0;
    }
    int askHowManyAcresToSell(int acresOwned){
        return 0;
    }
    int askHowMuchGrainToFeedPeople(int bushels){
        return 0;
    }
    int askHowManyAcresToPlant(int acresOwned, int population, int bushels){
        return 0;
    }

    public int plagueDeaths(int population) {  //15% chance of plague
        int deaths = 0;
        int chance = random.nextInt(100)+1; //generate a random # between 1 & 100
        if (chance <= 15 ){ deaths = population/2; }  // if chance is less than 15, trigger plague
        return deaths;
    }

    public int starvationDeaths(int population, int bushelsFedToPeople) { //takes in the current population and the players choice for bushels fed
        int peopleFed = bushelsFedToPeople/20; // 20 bushels needed per person
            if( peopleFed > population) {
                peopleFed = population;  //no bonus for overfeeding
            }
        return population-peopleFed; // deaths related to starvation
    }  //

    public boolean uprising(int population, int howManyPeopleStarved) {
        int percentStarved = (100*howManyPeopleStarved)/population;

        return (percentStarved >= 45); // we can simplify the lines, sure.  Let's just test the methods first
    }

    public int immigrants(int population, int acresOwned, int grainInStorage) { //

        return (20 * acresOwned + grainInStorage)/(100*population) + 1; //taken from ReadMe
    }

    public int harvest(int acres) { //test written to 1 parameter.  ReadMe uses and extra (int bushelsUsedAsSeed)
        int yield = random.nextInt(6)+1;  // random between 1 & 6

        return acres * yield;
    }

    public int grainEatenByRats(int bushels) {
        int chance = random.nextInt(100)+1;
        int grainChance = (random.nextInt(21)+10);
        int grainEaten = 0;
        if (chance <= 40){
            grainEaten = (bushels * grainChance)/100;
        }
        return grainEaten;
    }

    public int newCostOfLand() {

        return random.nextInt(7)+17; // generate a number between 17 & 23
    }

    //add an end game condition.  Either everyone died, or you survived 10 years

    //other methods go here
}