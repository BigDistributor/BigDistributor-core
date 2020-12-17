package com.bigdistributor.publisher;


import java.util.ArrayList;
import java.util.List;

public class Zoo {

    private List<Animal> animals = new ArrayList<>();
    private List<AnimalAddedListener> listners = new ArrayList<>();


    public void addAnimal(Animal animal){
        this.animals.add(animal);
        this.notifyAnimalAddedListener(animal);
    }

    public void registerAnimalAddedListener( AnimalAddedListener listener){
        this.listners.add(listener);
    }

    public void unregisterAnimalAddedListener( AnimalAddedListener listener){
        this.listners.remove(listener);
    }

    public void notifyAnimalAddedListener( Animal animal){
        this.listners.forEach(listener -> listener.onAnimalAdded(animal));
    }
}