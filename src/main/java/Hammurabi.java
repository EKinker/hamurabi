//package java;



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
        int totalDeaths = 0, totalPeople = 100;

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
            gameActive = !uprising(people, peopleStarved);
            immigrants = immigrants(people, landAcres, grainBushels);
            people += immigrants;
            bushelsHarvested = harvest(acresPlanted);
            grainBushels += bushelsHarvested;
            foodEatenByRats = grainEatenByRats(grainBushels);
            grainBushels -= foodEatenByRats;
            landValue = newCostOfLand();

            year++;
            totalDeaths+= peopleStarved;
            totalPeople += immigrants;
        }
        endGame(totalDeaths,totalPeople);
    }


    public void printYearlyStatus(int year, int peopleStarved, int immigrants, int people, int bushelsHarvested, int bushelsRatted, int totalBushels, int landAcres, int landValue, int landPlanted) { //start each round with an update
        int harvestRate;
        if (landPlanted == 0) {
            harvestRate = 0;
        } else {
            harvestRate = bushelsHarvested / landPlanted;
        }
        System.out.println(TextColor.PURPLE + "\n\n\nO great and wise Hammurabi!");
        System.out.printf(TextColor.BLUE + "You are in year %d of your ten year rule.\n" + reset(), year);
        System.out.println("In the previous year " + TextColor.RED + peopleStarved + reset() + " people starved to death.");
        System.out.println("In the previous year " + bold(immigrants)+ " people entered the kingdom.");
        System.out.println("The population is now " + bold(people)+" people.");
        System.out.println("We harvested " + bold(bushelsHarvested) + " bushels at " + bold(harvestRate) +  " bushels per acre.");
        System.out.println("Rats destroyed " + TextColor.RED + bushelsRatted + reset() + " bushels, leaving " + bold(totalBushels) +  " bushels in storage.");
        System.out.println("Our great city owns " + bold(landAcres) +" acres of land.");
        System.out.println("Land is currently worth " + bold(landValue) + " bushels per acre.");
        System.out.println(TextColor.BLUE + "\n\nYear " + year + reset());


    }

    public int landChange(int landPrice, int bushels, int land) {
        int change = askHowManyAcresToBuy(landPrice, bushels);
        if (change == 0) {
            change = -(askHowManyAcresToSell(land));
        }
        return change;
    }

    int askHowManyAcresToBuy(int price, int bushels) {
        System.out.println("\nLand costs " + bold(price) +  " bushels per acre this year.");
        System.out.println("You have " + bold(bushels) +  " bushels to spend.");

        int input = getNumber(TextColor.CYAN + "How many acres of land shall we purchase? " + reset());
        while ((input * price) > bushels) {
            input = getNumber("O wise ruler, this would cost us " + bold((input * price)) + " bushels of grain.  We do not have enough grain to purchase so much land! ");
        }
        if(input<0){
            System.out.println(TextColor.YELLOW+ "Perhaps you wish to sell some land instead? "+ reset());
            input = 0;
        }

        return input;
    }

    int askHowManyAcresToSell(int acresOwned) {
        System.out.println("\nWe own " + bold(acresOwned) + " acres of land");
        System.out.println("We should sell some land so our people have enough to eat.");

        int input = getNumber(TextColor.CYAN + "How many acres of land shall we sell? " + reset());
        while (input > acresOwned) {
            input = getNumber("O wise ruler, we do not have " + bold(input) + " acres to sell. ");
        }
        while (input < 0) {
            System.out.println(TextColor.YELLOW+ "Sir, the time to buy land is over.  We must wait until next year."+ reset());
            input = 0;
        }
        return input;
    }

    int askHowMuchGrainToFeedPeople(int bushels, int people) {  // need 20 grain per person
        System.out.println("\nOh wise leader, we have " + bold(people) + " people to feed and " + bold(bushels) + " bushels of grain.");
        System.out.println("We would need " + bold(people*20) + " bushels to feed every person.");
        int input = getNumber(TextColor.CYAN + "How many bushels of grain shall we feed our people? " + reset());
        while(input<0){
            input = getNumber(TextColor.YELLOW+ "Sir, surely you do not wish to rob our people of their food? "+ reset());
        }
        while (input > bushels) {
            input = getNumber("Wise leader, we do not have enough grain.");
        }
        return input;
    }

    int askHowManyAcresToPlant(int acresOwned, int population, int bushels) { // 2 bushels needed for each acre  //10 acres per person
        System.out.println("\nWise leader, we must plant crops to so we may have enough grain next year.");
        System.out.println("We own " + bold(acresOwned) + " acres of land.  We will need " + bold(acresOwned*2) +  " bushels to farm our land.");
        System.out.println("We currently have " + bold(bushels)  + " bushels of grain, enough to plant " + bold(bushels / 2) + " acres.");
        int input = getNumber(TextColor.CYAN + "How many acres shall we plant? " + reset());
        while (input < 0) {
            input = getNumber(TextColor.YELLOW+ "My humble apologies, wise leader.  I must have misheard your order. "+ reset());
        }
        while (input > acresOwned) {
            input = getNumber("Wise leader, we do not own that many acres! ");
        }
        while (input > bushels / 2) {
            input = getNumber("Wise leader, we do not have enough grain to plant that much! ");
        }
        while (input / 10 > population) {
            input = getNumber("Wise leader, we do not have enough people! We would need " + bold(input/10) +" farmers to plant that many acres! \n We currently have enough people to plant " + bold(population*10) +  " acres. ");
        }
        return input;
    }

    public int plagueDeaths(int population) {  //15% chance of plague
        int deaths = 0;
        int chance = random.nextInt(100) + 1; //generate a random # between 1 & 100
        if (chance <= 15) {
            deaths = population / 2;
            System.out.println(TextColor.BRIGHT_RED + "\n\nA plague has swept through the population! " + deaths + " of our people have died!" + reset());
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
            System.out.println(TextColor.BRIGHT_RED + "\n\nToo many of our people have starved. Your subjects have revolted!" + reset());
            return true;
        }

        return false;
    }

    public int immigrants(int population, int acresOwned, int grainInStorage) { //
        if (population == 0) {
            return 0;
        }
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

    public String bold(int input) {
        return TextColor.BRIGHT_WHITE+input+ TextColor.RESET;
    } // make the input bright white

    public String reset() {
        return TextColor.RESET;
    }  //reset text color

    public void everyoneDied() {
        System.out.println(TextColor.BRIGHT_RED + "\n\nSir! We forgot to feed our people!  Everyone is dead!" + reset());
            }


    public void endGame(int deaths, int people){
        int percentDied = (100*deaths)/people;
        System.out.println(TextColor.YELLOW+ "In your 10 years as ruler, "+percentDied+"% of our people died.");

        System.out.println(TextColor.YELLOW+ "\n\n********GAME OVER*******"+ reset());

    }
}