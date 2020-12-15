package com.winbeldon.model;

public class Details {
    private String First_name;
    private String Last_name;
    private String Hand;
    private String Birth_date;
    private String countryName;


    public void SetDetails(String First_Name, String Last_name, String Hand,
                      String Birth_date, String countryName){
        this.First_name = First_Name;
        this.Last_name = Last_name;
        this.Hand = Hand;
        this.Birth_date = Birth_date;
        this.countryName = countryName;

    }

    public String GetFirstName(){
        return this.First_name;
    }

    public String GetLastName(){
        return this.Last_name;
    }
    public String GetHand(){
        return this.Hand;
    }
    public String GetBirthDate(){
        return this.Birth_date;
    }
    public String GetCountry(){
        return this.countryName;
    }
    public String GetAll(){
        return this.First_name + " " + this.Last_name + " " +
                this.Birth_date + " " + this.Hand + " " + this.countryName;
    }





}
