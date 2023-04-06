import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/** This class generates the final cost and calculates costs according to the dates
 /*
 * Classname : Billing
 *
 * Version information : 1.0.0
 *
 * Date : 12-08-2022
 *
 * By Abhishek Jagadish s3911506
 */
class Billing{
    Scanner input = new Scanner(System.in);
    static int DailyPrice;
    static int WeeklyDiscount;
    static int ServiceFee;
    static int Insurance;
    static double total;
    static long Days;
    public static String ID;
    public static String Brand;
    public static String Model;
    public static String Type;
    public static String YOM;
    public static String Seats;
    public static String Color;
    public static long cost;
    public static long discount;
    public static String start;
    public static String drop;

    public ArrayList ChosenCar(ArrayList <ArrayList>chosencar) {
        String WD;
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("> Provide Dates");
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.print("Please provide pick-up date (dd/mm/yyyy):");
        String start = input.next();
        setStart(String.valueOf(start));
        System.out.print("Please provide return date (dd/mm/yyyy):");
        String drop = input.next();
        setDrop(String.valueOf(drop));
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date Start;
        try {
            Start = (formatter.parse(start));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Date Drop;
        try {
            Drop = formatter.parse(drop);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        long diffInMillies = (Start.getTime() - Drop.getTime());
        if (diffInMillies<0) {
            diffInMillies = Math.abs(diffInMillies);
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            setDays(diff);
        }
        else{
            System.out.println("Invalid Dates Please Check!");
            ChosenCar(chosencar);
        }

        setDailyPrice(Integer.parseInt((String) chosencar.get(0).get(7)));

        if (chosencar.get(0).contains("N/A")) {
            WD = "0";
            setWeeklyDiscount(Integer.parseInt(WD));
        } else {
            WD = (String) chosencar.get(0).get(10);
            setWeeklyDiscount(Integer.parseInt(WD));

        }
        setServiceFee(Integer.parseInt((String) chosencar.get(0).get(9)));
        setInsurance(Integer.parseInt((String) chosencar.get(0).get(8)));
        car(((String) chosencar.get(0).get(0)), ((String) chosencar.get(0).get(1)), ((String) chosencar.get(0).get(2)), ((String) chosencar.get(0).get(3)), ((String) chosencar.get(0).get(4)), ((String) chosencar.get(0).get(5)), ((String) chosencar.get(0).get(6)), ((String) chosencar.get(0).get(7)));
        getBill();

        return chosencar;
    }
    public void setDailyPrice(int DailyPrice){
        Billing.DailyPrice = DailyPrice;
    }

    public void setDays(long Days){
        Billing.Days = Days+1;
    }

    public void setWeeklyDiscount(int WeeklyDiscount){
        Billing.WeeklyDiscount = (100-WeeklyDiscount)/100;
    }
    public void setServiceFee(int ServiceFee){
        Billing.ServiceFee = ServiceFee;
    }

    public void setInsurance(int Insurance){
        Billing.Insurance = Insurance;
    }

    public void setStart(String Start){
        Billing.start = Start;
    }

    public void setDrop(String Drop){
        Billing.drop = Drop;
    }
    public void car(String ID,String Brand,String Model,String Type,String YOM,String Seats,String Color,String cost){
        Billing.ID = ID;
        Billing.Brand = Brand;
        Billing.Model = Model;
        Billing.Type = Type;
        Billing.YOM = YOM;
        Billing.Seats = Seats;
        Billing.Color = Color;
        Billing.cost = DailyPrice*Days;
    }

    public long getBill(){
        if(Days>=7) {
            Billing.total = ((long) DailyPrice * WeeklyDiscount+Insurance) * Days + ServiceFee;
            Billing.discount = Long.parseLong(String.valueOf(((long) DailyPrice *WeeklyDiscount)*Days));
            Vehicle vehicle = new Vehicle();
            vehicle.details(ID,Brand,Model,Type,YOM,Seats,Color,cost,discount, total,Insurance,ServiceFee, (int) Days,start,drop,DailyPrice);
            return (long) Billing.total;

        }
        else{
            Billing.total = (DailyPrice + Insurance) * Days + ServiceFee;
            Billing.discount = cost;
            Vehicle vehicle = new Vehicle();
            vehicle.details(ID,Brand,Model,Type,YOM,Seats,Color,cost, discount, total,Insurance,ServiceFee, (int) Days,start,drop, DailyPrice);
            return (long) Billing.total;

        }
    }
}


/** Filtering of csv file and storing the returned values happens here
 /*
 * Classname : Search
 *
 * Version information : 1.0.0
 *
 * Date : 12-08-2022
 *
 * By Abhishek Jagadish s3911506
 */
class Search {
    ArrayList<ArrayList> Filtered;

    {
        Filtered = new ArrayList<>();
    }
    ArrayList<ArrayList>newFilter = new ArrayList<>();
    ArrayList<ArrayList>chosencar = new ArrayList<>();
    Scanner input = new Scanner(System.in);

    static String Brand;
    static int Type_option;
    static int Number;
    public static final String delimiter = ",";



    public void setBrand(String Brand){
        Search.Brand = Brand;

    }
    public void setType_option(int Type_option){
        Search.Type_option = Type_option;
    }
    public void setNumber(int Number){
        Search.Number = Number;
    }


    public ArrayList FilterProcess( String csv) throws  IOException{
        String line;
        BufferedReader br = new BufferedReader(new FileReader(csv));

        String [] read;
        int count = 0;
        while ((line = br.readLine())!= null) {
            if (count == 0){
                count++;
                continue;
            }
            else {
                ArrayList<String> tep = new ArrayList<>();
                read = line.split(delimiter);
                int cnt = 0;
                for (String temp: read){
                    if (cnt<12){
                        tep.add(temp);
                        cnt++;
                        if(!Filtered.contains(tep)){
                            Filtered.add(tep);
                        }
                    }
                    else{
                        cnt=0;
                        tep.clear();
                    }
                }
            }
        }
        return Filtered;
    }
    public ArrayList BrandFilter(String Brand, String csv) throws IOException {
        FilterProcess(csv);
        for (ArrayList arrayList : Filtered) {
            if (arrayList.contains(Brand)) {
                newFilter.add(arrayList);
            }
        }
        if(newFilter.size()==0){
            Rental rental = new Rental();
            System.out.println("Brand does not exist");
            rental.setOption(1);
            rental.Selection(csv);
        }
        else {
            carchoice(newFilter);
        }
        return newFilter;
    }
    public ArrayList TypeFilter(int chose, String csv) throws IOException{
        FilterProcess(csv);
        Map<Integer, String> types = new HashMap<>();
        for (int j=0; j< Filtered.size();j++){
            types.put(j+1, String.valueOf(Filtered.get(j).get(3)));
        }
        String chosen = types.get(chose);
        for (ArrayList arrayList : Filtered) {
            if (arrayList.contains(chosen)) {
                newFilter.add(arrayList);
            }
        }
        carchoice(newFilter);
        return newFilter;
    }
    public ArrayList SeatingFilter(int number, String csv) throws IOException{
        FilterProcess(csv);
        Map<Integer, String> num = new HashMap<>();
        if (number>=1&&number<5){
            num.put(number,"4");
        } else if (number>5&&number<=7) {
            num.put(number,"7");
        } else if (number==5) {
            num.put(number,"5");
        }
        String passengers = num.get(number);
        for (ArrayList arrayList : Filtered) {
            if (arrayList.contains(passengers)) {
                newFilter.add(arrayList);
            }
        }
        carchoice(newFilter);
        return newFilter;
    }
    public void carchoice(ArrayList<ArrayList> newFilter) throws FileNotFoundException {
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("> Select from matching list");
        System.out.println("--------------------------------------------------------------------------------------");
        Map<Integer, ArrayList> Choice = new HashMap<>();
        for(int i=0;i< newFilter.size();i++){
            int j = i+1;
            System.out.println("   "+j+")"+" "+(newFilter.get(i).get(0))+" "+"-"+" "+""+(newFilter.get(i).get(1))+" "+(newFilter.get(i).get(2))+" "+(newFilter.get(i).get(3))+" with "+(newFilter.get(i).get(5))+" "+"seats");
            Choice.put(j,newFilter.get(i));
        }
        System.out.println("   "+(newFilter.size()+1)+")"+" "+"Go to main menu");
        System.out.print("Please Select: ");
        int carchosen = input.nextInt();
        if (carchosen==(newFilter.size()+1)){
            Main.main(new String[]{"Going to main menu"});
        } else if (carchosen>(newFilter.size()+1)){
            System.out.println("Please select the correct option from below!");
            carchoice(newFilter);
        }
        else {
            chosencar.add(Choice.get(carchosen));
            Billing bill = new Billing();
            bill.ChosenCar(chosencar);
        }
    }
}


/** Switch case happens here and incorrect option is handled
 /*
 * Classname : Rental
 *
 * Version information : 1.0.0
 *
 * Date : 12-08-2022
 *
 * By Abhishek Jagadish s3911506
 */
class Rental{
    Scanner input = new Scanner(System.in);
    static int option;
    static boolean rollback;


    public void setOption(int option){
        Rental.option = option;
    }

    public void setRollback(boolean rollback) {
        Rental.rollback = rollback;
    }
    public boolean getRollback(){
        return rollback;
    }

    public void Selection(String csv) throws IOException {
        if (option == 1){
            System.out.print("Please Provide a brand: ");
            String brand = input.next();
            Search filter = new Search();
            filter.setBrand(brand);
            filter.BrandFilter(brand,csv);
            setRollback(false);
        }
        else if (option == 2) {
            System.out.println("Browse by type of vehicle");
            System.out.print("   "+"1) Sedan\n"+"   "+"2) Hatch\n"+"   "+"3) SUV\n"+"   "+"4) Go to main menu\n");
            System.out.print("Please select: ");
            int chose = input.nextInt();
            if (chose==4){
                setRollback(true);
            }
            else {
                Search filter = new Search();
                filter.setType_option(chose);
                filter.TypeFilter(chose, csv);
                setRollback(false);
            }
        }
        else if (option == 3){
            boolean loop=true;
            while(loop) {
                System.out.print("Please Provide the number of passengers: ");
                int number = input.nextInt();
                if (number >=8 || number<=0) {
                    System.out.println("\nPlease Enter a valid number \n");
                } else {
                    Search filter = new Search();
                    filter.setNumber(number);
                    filter.SeatingFilter(number, csv);
                    setRollback(false);
                    loop=false;
                }
            }
        }
        else if (option == 4){
            setRollback(false);
        }
        else{
            System.out.println("Incorrect option! Please enter any of the valid inputs from below");
            setRollback(true);
        }
    }

}

/**This class displays the details of the car chosen by the user
 /*
 * Classname : Vehicle
 *
 * Version information : 1.0.0
 *
 * Date : 12-08-2022
 *
 * By Abhishek Jagadish s3911506
 */
class Vehicle {
    public void details(String ID, String Brand, String Model, String Type, String YOM, String Seats, String Color, long cost, long discount, double total, int insurance, int serviceFee, int Days, String start, String drop, int dailyPrice){
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("> Show Vehicle Details");
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.print("Vehicle:                   "+ID+"\n");
        System.out.print("Brand:                     "+Brand+"\n");
        System.out.print("Model:                     "+Model+"\n");
        System.out.print("Type of Vehicle:           "+Type+"\n");
        System.out.print("Year of Manufacture:       "+YOM+"\n");
        System.out.print("No of Seats:               "+Seats+"\n");
        System.out.print("Color:                     "+Color+"\n");
        System.out.print("Rental:                    "+Float.parseFloat(String.valueOf(cost))+"   "+"( $"+String.valueOf(dailyPrice)+" * "+String.valueOf(Days)+" Days )"+"\n");
        if(Days<7) {
            System.out.print("Discount Price:            " + Double.parseDouble(String.valueOf(discount)) + "   " + "(Discount is not applicable)" + "\n");
        }
        else{
            System.out.print("Discount Price:            " + Double.parseDouble(String.valueOf(discount)) + "   " + "(Discount applied)" + "\n");
        }
        System.out.print("Insurance:                 "+Double.parseDouble(String.valueOf((insurance*Days)))+"   "+"( $"+String.valueOf(insurance)+" * "+String.valueOf(Days)+" Days )"+"\n");
        System.out.print("Service Fee:               "+Double.parseDouble(String.valueOf(serviceFee))+"\n");
        System.out.print("Total:                     "+total+"\n");
        System.out.print("Would you like to reserve this vehicle? (Y/N)");
        Scanner input = new Scanner(System.in);
        String reserve = input.next();
        if (reserve.equalsIgnoreCase("N")){
            try {
                System.out.println("Reservation Cancelled");
                Main.main(new String[]{"Reservation Cancelled"});
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else if (reserve.equalsIgnoreCase("Y")) {
            Personal details = new Personal();
            details.PersonalDetails(Brand,Model,Type,Seats,start,drop,total);
        }
    }
}

/**
 * This is the personal class which collects all user details
 * /*
 * Classname : Personal
 * <p>
 * Version information : 1.0.0
 * <p>
 * Date : 12-08-2022
 * <p>
 * By Abhishek Jagadish s3911506
 */
class Personal {
    static String fname;
    static String lname;
    static String email;
    static int passengers;
    Scanner input = new Scanner(System.in);

    public void setFname(String fname, String seats) {
        Personal.fname = fname;
        System.out.print("Please provide you surname: ");
        String lastname = input.next();
        setLname(lastname, seats);
    }

    public void setLname(String lname, String seats) {
        Personal.lname = lname;
        System.out.print("Please provide you email address: ");
        String EmailID = input.next();
        ArrayList<String> address = new ArrayList<>();
        address.add(EmailID);
        ValidEmail(EmailID);
        for (String i : address) {
            if (ValidEmail(i)) {
                setEmail(EmailID, seats);
            } else {
                System.out.println("Invalid email!");
                setLname(lname, seats);
            }
        }

    }

    public static boolean ValidEmail(String email) {
        String emailRegex = "^[a-zA-Z\\d_+&*-]+(?:\\." +
                "[a-zA-Z\\d_+&*-]+)*@" +
                "(?:[a-zA-Z\\d-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern emailpattern = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return emailpattern.matcher(email).matches();
    }

    public void setEmail(String email, String seats) {
        int pass = 0;
        boolean val = true;
        Personal.email = email;
        while (val) {
            System.out.print("Please provide number of passengers: ");
            try {
                pass = input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Please enter a number");
            }
            if (pass <= Integer.parseInt(seats)) {
                val = false;
                setPassengers(pass);
            } else {
                System.out.print("\n" + "Number of passengers are more than available seats" + "\n");
                val = true;
            }
        }
    }

    public void setPassengers(int passengers) {
        Personal.passengers = passengers;
    }

    public void getDetails(String Brand, String Model, String Type, String Seats, String start, String drop, double total) {
        System.out.print("Confirm and Pay? (Y/N)");
        String pay = input.next();
        if (pay.equalsIgnoreCase("Y")) {
            System.out.println("-------------------------------------------------------------------------------------");
            System.out.println("> Congratulations! Your vehicle is booked. A receipt has been sent to your email.\n" +
                    "  We will soon be in touch before your pick-up date.");
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.print("Name:                      " + fname + " " + lname + "\n");
            System.out.print("Email:                     " + email + "\n");
            System.out.print("Your Vehicle:              " + Brand + " " + Model + " " + Type + " With " + Seats + " seats" + "\n");
            System.out.print("Number of passengers:      " + passengers + "\n");
            System.out.print("Pick-up date:              " + start + "\n");
            System.out.print("Drop date:                 " + drop + "\n");
            System.out.print("Total payment              " + total + "\n");
        } else {
            try {
                System.out.println("Reservation Cancelled");
                Main.main(new String[]{"Main"});
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void PersonalDetails(String Brand, String Model, String Type, String Seats, String start, String drop, double total) {
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("> Provide personal information");
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.print("Please provide you firstname: ");
        String firstname = input.next();
        setFname(firstname, Seats);
        getDetails(Brand, Model, Type, Seats, start, drop, total);
    }

}


public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        boolean rollback = true;
        String csv;
        try {
            csv = "Fleet.csv";
            new BufferedReader(new FileReader(csv));
        }catch (FileNotFoundException e){
            csv = "src/Fleet.csv";
            new BufferedReader(new FileReader(csv));
        }
        System.out.println("Welcome to MyCar!");
        while (rollback) {
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.println("> Select from main menu");
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.printf("%5s%-6s", "1)", " Search by brand\n");
            System.out.printf("%5s%-6s", "2)", " Browse by vehicle type\n");
            System.out.printf("%5s%-6s", "3)", " Filter by number of passengers\n");
            System.out.printf("%5s%-6s", "4)", " Exit\n");
            System.out.print("Please select: ");
            Scanner input = new Scanner(System.in);
            int choice = 0;
            try {
                choice  = input.nextInt();
            } catch (InputMismatchException e){
                System.out.println("Error expected the number on the left");
            }
            Rental Choice = new Rental();
            Choice.setOption(choice);
            try {
                Choice.Selection(csv);
            } catch (IOException e) {
                System.out.println("IO error with CSV file");
            }
            rollback = Choice.getRollback();
        }
    }
}
