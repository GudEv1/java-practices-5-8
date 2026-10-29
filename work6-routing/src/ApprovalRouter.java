package src;

public class ApprovalRouter {
    // Вариант 7: порог финансового согласования = 400 000 руб.
    private static final long FINANCE_THRESHOLD = 400_000L;

    public static ApprovalRoute determineRoute(
            long amount,
            boolean urgent,
            RiskLevel risk,
            boolean containsPersonalData) {

        // Проверка входных данных
        if (amount < 0) {
            throw new IllegalArgumentException("Сумма не может быть отрицательной: " + amount);
        }

        // Приоритет от самого строгого правила к общему
        if (containsPersonalData) {
            return ApprovalRoute.SECURITY_REVIEW;
        }
        if (risk == RiskLevel.HIGH) {
            return ApprovalRoute.DIRECTOR_REVIEW;
        }
        if (amount >= FINANCE_THRESHOLD) {
            return ApprovalRoute.FINANCE_REVIEW;
        }
        if (urgent && amount < FINANCE_THRESHOLD) {
            return ApprovalRoute.FAST_TRACK;
        }
        return ApprovalRoute.STANDARD;
    }

    public static void main(String[] args) {
        SelfCheck.run();
        System.out.println("=== Маршрутизация заявок (Вариант 7: порог FINANCE = 400 000) ===\n");

        // Тестовые сценарии
        printRoute("Типовой (сумма 300 000, не срочная, LOW риск, без ПД)",
                300_000L, false, RiskLevel.LOW, false);

        printRoute("Персональные данные (сумма 100 000, срочная)",
                100_000L, true, RiskLevel.LOW, true);

        printRoute("Высокий риск (сумма 200 000)",
                200_000L, false, RiskLevel.HIGH, false);

        printRoute("Сумма на границе 400 000 (>= порога)",
                400_000L, false, RiskLevel.LOW, false);

        printRoute("Сумма 399 999 (ниже порога), срочная",
                399_999L, true, RiskLevel.LOW, false);

        printRoute("Сумма 500 000, без ПД, LOW риск",
                500_000L, false, RiskLevel.LOW, false);

        printRoute("Сумма 0, не срочная, LOW риск",
                0L, false, RiskLevel.LOW, false);

        printRoute("Отрицательная сумма (ошибка)",
                -100L, false, RiskLevel.LOW, false);
    }

    private static void printRoute(String description, long amount,
                                    boolean urgent, RiskLevel risk,
                                    boolean containsPersonalData) {
        System.out.println("Тест: " + description);
        try {
            ApprovalRoute route = determineRoute(amount, urgent, risk, containsPersonalData);
            System.out.println("  → Маршрут: " + route);
        } catch (Exception e) {
            System.out.println("  → Ошибка: " + e.getMessage());
        }
        System.out.println();
    }
}