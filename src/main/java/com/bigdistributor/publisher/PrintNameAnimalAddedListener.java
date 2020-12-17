package com.bigdistributor.publisher;

public class PrintNameAnimalAddedListener implements AnimalAddedListener {
    @Override
    public void onAnimalAdded(Animal animal) {
        System.out.println("added with name : " +animal.getName());
    }
}
