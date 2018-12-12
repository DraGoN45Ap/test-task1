package main.service;

import main.entity.OperationDTO;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static main.Main.USER_DIR;

/**
 * Operation service.
 */
public class OperationService {

    /**
     * Singleton constructor.
     */
    private OperationService() {
    }

    /**
     * Method for create operation data from the past year.
     *
     * @param pointOfSale    list of point of sales numbers.
     * @param operationLimit limit for create operations.
     * @param outFilename    output filename.
     */
    public static void createOperationsList(List<Integer> pointOfSale, Integer operationLimit,
                                            String outFilename) {
        try (FileWriter writer = new FileWriter(USER_DIR.concat(outFilename))) {
            for (int i = 0; i <= operationLimit; i++) {
                OperationDTO operationDTO = createOperation(pointOfSale, operationLimit);
                writer.write(operationDTO.toString());
            }
        } catch (IOException e) {
            System.out.println("Cannot write data to the file: " + outFilename);
        }
    }

    /**
     * Create random operation by random point of sale in the past year.
     *
     * @param pointOfSale    list of points of sales.
     * @param operationLimit operation limit.
     * @return random operation object.
     */
    private static OperationDTO createOperation(List<Integer> pointOfSale, Integer operationLimit) {
        Random random = new Random();
        long minDay = LocalDate.of(LocalDate.now()
                .minus(1, ChronoUnit.YEARS).getYear(), 1, 1).toEpochDay();
        long maxDay = LocalDate.of(LocalDate.now().getYear(), 1, 1).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        LocalDateTime time = LocalDateTime.of(LocalDate.ofEpochDay(randomDay), LocalTime.of(random
                .nextInt(24), random.nextInt(60), random
                .nextInt(60), random.nextInt(999999999 + 1)));
        return new OperationDTO(time.withNano(0), pointOfSale.get(random.nextInt(pointOfSale.size())),
                random.nextInt(operationLimit), calculateRandomPrice());
    }

    /**
     * Create random price data.
     *
     * @return price.
     */
    private static double calculateRandomPrice() {
        return Math.floor(10000 + Math.random() * (100000 - 10000) * 100) / 100;
    }
}