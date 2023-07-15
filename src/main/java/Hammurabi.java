//package hammurabi;

import org.w3c.dom.Text;

import java.sql.SQLOutput;
import java.util.Random;
import java.util.Scanner;


public class Hammurabi {
    static Random random = new Random();
    Scanner scanner = new Scanner(System.in);

    //int acresPurchased = 0;


    public static void main(String[] args) {
        new Hammurabi().playGame();
    }

    void playGame() {
        // declare local variables here: grain, population, etc.
        int people = 100, grainBushels = 2800, landAcres = 1000, landValue = 19, year = 1;
        int peopleStarved = 0, immigrants = 5, bushelsHarvested = 3000, foodEatenByRats = 200;

        while (year <= 10) {//add loop while game is active
            printYearlyStatus(year, peopleStarved, immigrants, people, bushelsHarvested, foodEatenByRats, grainBushels, landAcres, landValue);
            int landChange;
            if (( landChange = askHowManyAcresToBuy(landValue, grainBushels))==0){
                landChange = -(askHowManyAcresToSell(landAcres));

            }

//          process(landChange);



        }
    }

    private void process(int changeInLand) { // update values based on player input

    }


    public void printYearlyStatus(int year, int peopleStarved, int immigrants, int people, int bushelsHarvested, int bushelsRatted, int totalBushels, int landAcres, int landValue) { //start each round with an update

        System.out.println(TextColor.TEXT_PURPLE + "O great Hammurabi!");
        System.out.printf(TextColor.TEXT_BLUE + "You are in year %d of your ten year rule.\n" + TextColor.TEXT_RESET, year);
        System.out.println("In the previous year " + TextColor.TEXT_RED + peopleStarved + TextColor.TEXT_RESET + " people starved to death.");
        System.out.printf("In the previous year %d people entered the kingdom.\n", immigrants);
        System.out.printf("The population is now %d.\n", people);
        System.out.printf("We harvested %d bushels at 3 bushels per acre.\n", bushelsHarvested);
        System.out.println("Rats destroyed " + TextColor.TEXT_RED + bushelsRatted + TextColor.TEXT_RESET + " bushels, leaving " + TextColor.TEXT_BRIGHT_WHITE + totalBushels + TextColor.TEXT_RESET + " bushels in storage.");
        System.out.println("city owns " + landAcres + " acres of land.");
//        Land is currently worth 19 bushels per acre.
        System.out.println(TextColor.TEXT_BLUE + "\n\nYear " + year + TextColor.TEXT_RESET);


    }


    int askHowManyAcresToBuy(int price, int bushels) {
        System.out.println("Land costs " + TextColor.TEXT_BRIGHT_WHITE + price + TextColor.TEXT_RESET + " bushels per acre this year.");
        System.out.println("You have " + TextColor.TEXT_BRIGHT_WHITE + bushels + TextColor.TEXT_RESET + " bushels to spend.");
        System.out.println(TextColor.TEXT_CYAN + "How many acres of land shall we purchase?" + TextColor.TEXT_RESET);
        int input = scanner.nextInt();
        while ((input * price) > bushels) {
            System.out.println("O wise ruler, this would cost us " + (input * price) + " bushels of grain.  We do not have enough grain to purchase so much land!");
            input = scanner.nextInt();
        }
        return input;
    }

    int askHowManyAcresToSell(int acresOwned) {
        System.out.println("We own " + TextColor.TEXT_BRIGHT_WHITE + acresOwned + TextColor.TEXT_RESET + " acres of land");
        System.out.println("Perhaps we should sell some land so our people have enough to eat.");
        System.out.println(TextColor.TEXT_CYAN + "How many acres of land shall we sell?" + TextColor.TEXT_RESET);
        int input = scanner.nextInt();
        if (input > acresOwned) {
            System.out.println("O wise ruler, we do not have " + input + " acres to sell.");
            input = scanner.nextInt();
        }
        return input;
    }

    int askHowMuchGrainToFeedPeople(int bushels) {
        return 0;
    }

    int askHowManyAcresToPlant(int acresOwned, int population, int bushels) {
        return 0;
    }

    public int plagueDeaths(int population) {  //15% chance of plague
        int deaths = 0;
        int chance = random.nextInt(100) + 1; //generate a random # between 1 & 100
        if (chance <= 15) {
            deaths = population / 2;
        }  // if chance is less than 15, trigger plague
        return deaths;
    }

    public int starvationDeaths(int population, int bushelsFedToPeople) { //takes in the current population and the players choice for bushels fed
        int peopleFed = bushelsFedToPeople / 20; // 20 bushels needed per person
        if (peopleFed > population) {
            peopleFed = population;  //no bonus for overfeeding
        }
        return population - peopleFed; // deaths related to starvation
    }  //

    public boolean uprising(int population, int howManyPeopleStarved) {
        int percentStarved = (100 * howManyPeopleStarved) / population;

        return (percentStarved >= 45); // we can simplify the lines, sure.  Let's just test the methods first
    }

    public int immigrants(int population, int acresOwned, int grainInStorage) { //

        return (20 * acresOwned + grainInStorage) / (100 * population) + 1; //taken from ReadMe
    }

    public int harvest(int acres) { //test written to 1 parameter.  ReadMe uses and extra (int bushelsUsedAsSeed)
        int yield = random.nextInt(6) + 1;  // random between 1 & 6

        return acres * yield;
    }

    public int grainEatenByRats(int bushels) {

        if ((random.nextInt(100) + 1) <= 40) {
            return (bushels * (random.nextInt(21) + 10)) / 100;
        }
        return 0;
    }

    public int newCostOfLand() {

        return random.nextInt(7) + 17; // generate a number between 17 & 23
    }

    //add an end game condition.  Either everyone died, or you survived 10 years

    //other methods go here


}