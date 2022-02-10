package com.vilson.animals;

import java.util.ArrayList;
import java.util.List;

import static com.vilson.animals.LiteralConstants.*;

public class HardcodedAnimalsProvider implements AnimalsProvider {

    private final List<Animal> animals;

    public HardcodedAnimalsProvider() {
        animals = new ArrayList<>();
        addDogs();
        addCats();
        addChickens();
        addRoosters();
        addParrots();
    }

    @Override
    public List<Animal> getAnimals() {
        return new ArrayList<>(animals);
    }

    private void addDogs() {
        animals.add(new Dog("Rex", ROYAL_CANIN, SHEPERD));
        animals.add(new Dog("Max", PURINA_ONE, SHEPERD));
        animals.add(new Dog("Tom", ROYAL_CANIN, HUSKY));
        animals.add(new Dog("Jay", PURINA_ONE, HUSKY));
    }

    private void addCats() {
        animals.add(new Cat("Zoe", NINE_LIVES));
        animals.add(new Cat("Ada", PURINA_FRISKIES));
    }

    private void addChickens() {
        animals.add(new Chicken("Meg", PURINA_LAYENA, 0.4, true));
        animals.add(new Chicken("Lis", MANNA_PRO, 0.35, true));
        animals.add(new Chicken("Emi", PURINA_LAYENA, 0.25, false));
        animals.add(new Chicken("Lua", MANNA_PRO, 0.3, false));
    }

    private void addRoosters() {
        animals.add(new Rooster("Bob", MANNA_PRO, 0.5));
    }

    private void addParrots() {
        animals.add(new Parrot("Mac", LAFEBER_ORIGINAL, 0.33, true));
        animals.add(new Parrot("Alf", KAYTEE_FIESTA, 0.25, false));
    }
}
