//package hammurabi;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;


public class Hammurabi {
    static Random random = new Random();
    Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {
        new Hammurabi().playGame();
    }

    void playGame() {
        // declare local variables here: grain, population, etc.
        int people = 100, grainBushels = 2800, landAcres = 1000, landValue = 19, immigrants = 5, bushelsHarvested = 3000, foodEatenByRats = 200, year = 1;
        int peopleStarved = 0, changeToLand, bushelsFed, acresPlanted = 1000;
        boolean gameActive = true;

        while (year <= 10 && gameActive) {//add loop while game is active
            printYearlyStatus(year, peopleStarved, immigrants, people, bushelsHarvested, foodEatenByRats, grainBushels, landAcres, landValue, acresPlanted);
            //player input
            changeToLand = landChange(landValue, grainBushels, landAcres);
            landAcres += changeToLand;
            grainBushels -= (changeToLand * landValue);
            bushelsFed = askHowMuchGrainToFeedPeople(grainBushels, people);
            grainBushels -= bushelsFed;
            acresPlanted = askHowManyAcresToPlant(landAcres, people, grainBushels);
            grainBushels -= acresPlanted * 2;

            //Year End Stuff
            people -= plagueDeaths(people); //15% chance of plague
            peopleStarved = starvationDeaths(people, bushelsFed);
            people -= peopleStarved;
            if (uprising(people, peopleStarved)) {
                gameActive = false;
            }
            immigrants = immigrants(people, landAcres, grainBushels);
            people += immigrants;
            bushelsHarvested = harvest(acresPlanted);
            grainBushels += bushelsHarvested;
            foodEatenByRats = grainEatenByRats(grainBushels);
            grainBushels -= foodEatenByRats;
            landValue = newCostOfLand();


            year++;
        }
    }


    public void printYearlyStatus(int year, int peopleStarved, int immigrants, int people, int bushelsHarvested, int bushelsRatted, int totalBushels, int landAcres, int landValue, int landPlanted) { //start each round with an update

        System.out.println(TextColor.PURPLE + "\n\n\nO great and wise Hammurabi!");
        System.out.printf(TextColor.BLUE + "You are in year %d of your ten year rule.\n" + _b(), year);
        System.out.println("In the previous year " + TextColor.RED + peopleStarved + _b() + " people starved to death.");
        System.out.println("In the previous year " + b() + immigrants + _b() + " people entered the kingdom.");
        System.out.println("The population is now " + b() + people + _b() + " people.");
        System.out.println("We harvested " + b() + bushelsHarvested + _b() + " bushels at " + b() + bushelsHarvested / landPlanted + _b() + " bushels per acre.");
        System.out.println("Rats destroyed " + TextColor.RED + bushelsRatted + _b() + " bushels, leaving " + b() + totalBushels + _b() + " bushels in storage.");
        System.out.println("Our great city owns " + b() + landAcres + _b() + " acres of land.");
        System.out.println("Land is currently worth " + b() + landValue + _b() + " bushels per acre.");
        System.out.println(TextColor.BLUE + "\n\nYear " + year + _b());


    }

    public int landChange(int landPrice, int bushels, int land) {
        int change = askHowManyAcresToBuy(landPrice, bushels);
        if (change == 0) {
            change = -(askHowManyAcresToSell(land));
        }
        return change;
    }

    int askHowManyAcresToBuy(int price, int bushels) {
        System.out.println("\nLand costs " + b() + price + _b() + " bushels per acre this year.");
        System.out.println("You have " + b() + bushels + _b() + " bushels to spend.");

        int input = getNumber(TextColor.CYAN + "How many acres of land shall we purchase? " + _b());
        while ((input * price) > bushels) {
            input = getNumber("O wise ruler, this would cost us " + (input * price) + " bushels of grain.  We do not have enough grain to purchase so much land! ");
        }
        return input;
    }

    int askHowManyAcresToSell(int acresOwned) {
        System.out.println("\nWe own " + b() + acresOwned + _b() + " acres of land");
        System.out.println("Perhaps we should sell some land so our people have enough to eat.");

        int input = getNumber(TextColor.CYAN + "How many acres of land shall we sell? " + _b());
        if (input > acresOwned) {
            input = getNumber("O wise ruler, we do not have " + input + " acres to sell. ");
        }
        return input;
    }

    int askHowMuchGrainToFeedPeople(int bushels, int people) {  // need 20 grain per person
        System.out.println("\nOh wise leader, we have " + b() + people + _b() + " people to feed and " + b() + bushels + _b() + " bushels of grain.");
        System.out.println("We would need " + b() + (people * 20) + _b() + " bushels to feed every person.");
        int input = getNumber(TextColor.CYAN + "How many bushels of grain shall we feed our people? " + _b());
        if (input > bushels) {
            input = getNumber("Wise leader, we do not have enough grain.");
        }
        return input;
    }

    int askHowManyAcresToPlant(int acresOwned, int population, int bushels) { // 2 bushels needed for each acre  //10 acres per person
        System.out.println("\nWise leader, we must plant crops to so we may have enough grain next year.");
        System.out.println("We own " + b() + acresOwned + _b() + " acres of land.  We will need " + b() + (acresOwned * 2) + _b() + " bushels to farm our land.");
        System.out.println("We currently have " + b() + bushels + _b() + " bushels of grain, enough to plant " + bushels / 2 + " acres.");
        int input = getNumber(TextColor.CYAN + "How many acres shall we plant? " + _b());
        if (input > acresOwned) {
            input = getNumber("Wise leader, we do not own that many acres! ");
        } else if (input > bushels / 2) {
            input = getNumber("Wise leader, we do not have enough grain to plant that much! ");
        }
        if (input / 10 > population) {
            input = getNumber("Wise leader, we do not have enough people! We would need " + b() + (input / 10) + _b() + " farmers to plant that many acres! \n We currently have enough people to plant " + b() + population * 10 + _b() + " acres. ");
        }
        return input;
    }

    public int plagueDeaths(int population) {  //15% chance of plague
        int deaths = 0;
        int chance = random.nextInt(100) + 1; //generate a random # between 1 & 100
        if (chance <= 15) {
            deaths = population / 2;
            System.out.println(TextColor.BRIGHT_RED + "\n\nA plague has swept over the population! " + deaths + " of our people have died!" + _b());
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
        if (population == 0) {
            everyoneDied();
            return true;
        }
            int percentStarved = (100 * howManyPeopleStarved) / population;
            if (percentStarved >= 45) {
                System.out.println(TextColor.BRIGHT_RED + "\n\nToo many of our people have starved. Your subjects have revolted!" + _b());
                return true;
            }

        return false;
    }

    public int immigrants(int population, int acresOwned, int grainInStorage) { //
        if (population == 0){return 0;}
        return (20 * acresOwned + grainInStorage) / (100 * population) + 1; //taken from ReadMe
    }

    public int harvest(int acres) { //test written to 1 parameter.  ReadMe uses an extra (int bushelsUsedAsSeed)
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

    int getNumber(String message) {
        while (true) {
            System.out.print(message);
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("\"" + scanner.next() + "\" isn't a number!");
            }
        }

    }

    public String b() { //bold
        return TextColor.BRIGHT_WHITE;
    }

    public String _b() { //unbold/reset
        return TextColor.RESET;
    }

    public void everyoneDied(){
        System.out.println(TextColor.BRIGHT_RED + "\n\nSir! We forgot to feed our people!  Everyone is dead!" + _b());

    }

}