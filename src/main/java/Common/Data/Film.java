package Common.Data;

import java.util.ArrayList;

public class Film {
    protected String title;
    protected String description;
    protected int year;
    protected int minutes;
    protected String director;
    protected ArrayList<Actor>actors;
    protected ArrayList<Category>categories;
    public Film(String title, String description,int year,int minutes,String director,ArrayList<Actor>actors,ArrayList<Category>categories){
        this.description=description;
        this.title=title;
        this.minutes=minutes;
        this.year=year;
        this.director=director;
        this.actors=actors;
        this.categories=categories;
    }
    public void setTitle(String title){
        this.title=title;
    }
    public void setDescription(String description){
        this.description=description;
    }
    public void setYear(int year){
        this.year=year;
    }
    public void setMinutes(int minutes){
        this.minutes=minutes;
    }
    public void setDirector(String director){
        this.director=director;
    }
    public void setCategories(ArrayList<Category>categories){
        this.categories=categories;
    }
    public void setActors(ArrayList<Actor>actors){
        this.actors=actors;
    }

    public String getTitle(){
        return title;
    }

    public ArrayList<Actor> getActors() {
        return actors;
    }

    public int getYear() {
        return year;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public int getMinutes() {
        return minutes;
    }

    public String getDescription() {
        return description;
    }

    public String getDirector() {
        return director;
    }
    @Override
    public String toString() {
        return "Film{" +
                "title='" + title +
                ", description=" + description +
                ", minutes=" + minutes+
                ",year="+year+",director="+director+" [actors=" + actors + "]"
                +" [actors=" + categories + "]"+"}";
    }

}
