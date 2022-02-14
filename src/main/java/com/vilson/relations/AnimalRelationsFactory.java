package com.vilson.relations;

import com.vilson.animals.Animal;

interface AnimalRelationsFactory {
    AnimalRelations getNewInstanceFor(Animal animal);
}
