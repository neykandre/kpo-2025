package hse.studying.zoo2.application.ports.in;

/**
 * Use case: retrieve overall zoo statistics.
 */
public interface GetZooStatisticsUseCase {
    /**
     * Retrieves the total number of animals in the zoo.
     * @return the total number of animals
     */
    int getTotalAnimals();

    /**
     * Retrieves the total number of enclosures in the zoo.
     * @return the total number of enclosures
     */
    int getTotalEnclosures();

    /**
     * Retrieves the number of free enclosures in the zoo.
     * @return the number of free enclosures
     */
    int getFreeEnclosures();

    /**
     * Retrieves the total number of feedings scheduled for the future.
     * @return the total number of feedings
     */
    int getTotalUpcomingFeedings();
}
