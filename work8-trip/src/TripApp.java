import java.time.LocalDate;

public class TripApp {
    public static void main(String[] args) {
        SelfCheck.run();

        System.out.println("=== Оформление командировок (Вариант 7: MIN_REASON = 40 символов) ===\n");

        // Тест 1: Корректный полный цикл
        System.out.println("Тест 1: Корректный полный цикл (DRAFT → SUBMITTED → APPROVED)");
        BusinessTrip trip1 = new BusinessTrip("Москва", LocalDate.of(2026, 10, 1), LocalDate.of(2026, 10, 5));
        System.out.println("  Статус: " + trip1.getStatus());
        trip1.submit();
        System.out.println("  После submit: " + trip1.getStatus());
        trip1.approve();
        System.out.println("  После approve: " + trip1.getStatus());

        // Тест 2: Отклонение с короткой причиной (ошибка варианта 7)
        System.out.println("\nТест 2: Отклонение с короткой причиной (10 символов)");
        BusinessTrip trip2 = new BusinessTrip("СПб", LocalDate.of(2026, 11, 1), LocalDate.of(2026, 11, 3));
        trip2.submit();
        try {
            trip2.reject("Короткая");
        } catch (Exception e) {
            System.out.println("  → Ошибка: " + e.getMessage());
        }

        // Тест 3: Отклонение с достаточной причиной (40 символов)
        System.out.println("\nТест 3: Отклонение с причиной ровно 40 символов");
        BusinessTrip trip3 = new BusinessTrip("Казань", LocalDate.of(2026, 12, 1), LocalDate.of(2026, 12, 4));
        trip3.submit();
        String reason40 = "Бюджет подразделения исчерпан на текущий квартал"; // ровно 40+ символов
        trip3.reject(reason40);
        System.out.println("  Статус после reject: " + trip3.getStatus());
        System.out.println("  Причина: " + trip3.getRejectionReason());

        // Тест 4: Отмена из DRAFT
        System.out.println("\nТест 4: Отмена из DRAFT");
        BusinessTrip trip4 = new BusinessTrip("Сочи", LocalDate.of(2027, 1, 10), LocalDate.of(2027, 1, 15));
        trip4.cancel();
        System.out.println("  Статус после cancel: " + trip4.getStatus());

        // Тест 5: Попытка approve из DRAFT (ошибка)
        System.out.println("\nТест 5: Попытка approve из DRAFT");
        BusinessTrip trip5 = new BusinessTrip("Уфа", LocalDate.of(2027, 2, 1), LocalDate.of(2027, 2, 5));
        try {
            trip5.approve();
        } catch (Exception e) {
            System.out.println("  → Ошибка: " + e.getMessage());
        }

        // Тест 6: Попытка submit дважды
        System.out.println("\nТест 6: Попытка submit дважды");
        BusinessTrip trip6 = new BusinessTrip("Екб", LocalDate.of(2027, 3, 1), LocalDate.of(2027, 3, 7));
        trip6.submit();
        try {
            trip6.submit();
        } catch (Exception e) {
            System.out.println("  → Ошибка: " + e.getMessage());
        }

        // Тест 7: Невалидные даты
        System.out.println("\nТест 7: Дата начала позже даты окончания");
        try {
            BusinessTrip trip7 = new BusinessTrip("Омск", LocalDate.of(2027, 5, 10), LocalDate.of(2027, 5, 5));
        } catch (Exception e) {
            System.out.println("  → Ошибка: " + e.getMessage());
        }

        // Тест 8: Пустое назначение
        System.out.println("\nТест 8: Пустое назначение");
        try {
            BusinessTrip trip8 = new BusinessTrip("", LocalDate.of(2027, 6, 1), LocalDate.of(2027, 6, 3));
        } catch (Exception e) {
            System.out.println("  → Ошибка: " + e.getMessage());
        }
    }
}