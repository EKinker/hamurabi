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
        printYearlyStatus();
        getPlayerInput();
        process();

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
        float chance = random.nextFloat()*100; //generate a random # between 1 & 100
        if (chance < 15 ){ deaths = population/2; }
        return deaths;
    }

    public int starvationDeaths(int i, int j) { //edit
        return 0;
    }  //

    public boolean uprising(int i, int j) { //edit
        return false;
    }

    public int immigrants(int i, int j, int k) { //edit
        return 0;
    }

    public int harvest(int i) {
        return 0;
    }

    public int grainEatenByRats(int i) {
        return 0;
    }

    public int newCostOfLand() {
        return 0;
    }

    //add an end game condition.  Either everyone died, or you survived 10 years

    //other methods go here
}