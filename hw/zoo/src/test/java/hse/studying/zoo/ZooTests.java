package hse.studying.zoo;

import hse.studying.zoo.domains.*;
import hse.studying.zoo.factories.HerbivoreFactory;
import hse.studying.zoo.factories.PredatorFactory;
import hse.studying.zoo.factories.ThingFactory;
import hse.studying.zoo.params.PredatorParams;
import hse.studying.zoo.params.HerbivoreParams;
import hse.studying.zoo.params.ThingParams;
import hse.studying.zoo.services.Clinic;
import hse.studying.zoo.services.Zoo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ZooTests {

    @Autowired
    private Zoo zoo;

    @Autowired
    private Clinic clinic;

    @Autowired
    private PredatorFactory predatorFactory;

    @Autowired
    private HerbivoreFactory herbivoreFactory;

    @Autowired
    private ThingFactory thingFactory;

    @BeforeEach
    void setUp() {
        zoo.getAnimals().clear();
        zoo.getThings().clear();
    }

    @Test
    void testAddHealthyAnimal() {
        Animal healthyTiger = predatorFactory.create("Tiger", new PredatorParams(50, 100, 1));
        zoo.addAnimal(healthyTiger);
        assertTrue(zoo.getAnimals().contains(healthyTiger));
    }

    @Test
    void testAddUnhealthyAnimal() {
        Animal sickRabbit = herbivoreFactory.create("Rabbit", new HerbivoreParams(0, 10, 5, 2));
        zoo.addAnimal(sickRabbit);
        assertFalse(zoo.getAnimals().contains(sickRabbit));
    }

    @Test
    void testAddThing() {
        Thing table = thingFactory.create("Table", new ThingParams(3));
        zoo.addThing(table);
        assertTrue(zoo.getThings().contains(table));
    }

    @Test
    void testRepeatedRegisterAnimal() {
        assertThrows(IllegalArgumentException.class, () -> predatorFactory.register("Tiger", Tiger::new));
    }

    @Test
    void testAddUnregisteredAnimal() {
        assertThrows(IllegalArgumentException.class, () -> herbivoreFactory.create("UnregisteredAnimal", new HerbivoreParams(1, 1, 1, 1)));
    }
}
