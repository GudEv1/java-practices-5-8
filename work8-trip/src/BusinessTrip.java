import java.time.LocalDate;

public class BusinessTrip {
    // Вариант 7: минимальная длина причины отклонения = 40 символов
    private static final int MIN_REJECTION_REASON_LENGTH = 40;

    private TripStatus status = TripStatus.DRAFT;
    private String destination;
    private LocalDate startDate;
    private LocalDate endDate;
    private String rejectionReason;

    public BusinessTrip(String destination, LocalDate startDate, LocalDate endDate) {
        if (destination == null || destination.trim().isEmpty()) {
            throw new IllegalArgumentException("Назначение не может быть пустым");
        }
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Даты не могут быть null");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Дата начала должна быть раньше даты окончания");
        }
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void submit() {
        if (this.status != TripStatus.DRAFT) {
            throw new IllegalStateException(
                "Нельзя отправить: текущий статус " + status + ", ожидается DRAFT");
        }
        if (destination == null || destination.trim().isEmpty()) {
            throw new IllegalStateException("Назначение обязательно для отправки");
        }
        if (startDate == null || endDate == null) {
            throw new IllegalStateException("Даты обязательны для отправки");
        }
        this.status = TripStatus.SUBMITTED;
    }

    public void approve() {
        if (this.status != TripStatus.SUBMITTED) {
            throw new IllegalStateException(
                "Нельзя одобрить: текущий статус " + status + ", ожидается SUBMITTED");
        }
        this.status = TripStatus.APPROVED;
    }

    public void reject(String reason) {
        if (this.status != TripStatus.SUBMITTED) {
            throw new IllegalStateException(
                "Нельзя отклонить: текущий статус " + status + ", ожидается SUBMITTED");
        }
        if (reason == null || reason.trim().length() < MIN_REJECTION_REASON_LENGTH) {
            throw new IllegalArgumentException(
                "Причина отклонения должна содержать минимум " + MIN_REJECTION_REASON_LENGTH + 
                " символов (сейчас: " + (reason == null ? 0 : reason.trim().length()) + ")");
        }
        this.rejectionReason = reason;
        this.status = TripStatus.REJECTED;
    }

    public void cancel() {
        if (this.status == TripStatus.COMPLETED) {
            throw new IllegalStateException("Нельзя отменить завершённую командировку");
        }
        if (this.status != TripStatus.DRAFT && 
            this.status != TripStatus.SUBMITTED && 
            this.status != TripStatus.APPROVED) {
            throw new IllegalStateException(
                "Нельзя отменить: текущий статус " + status);
        }
        this.status = TripStatus.CANCELLED;
    }

    public void complete() {
        if (this.status != TripStatus.APPROVED) {
            throw new IllegalStateException(
                "Нельзя завершить: текущий статус " + status + ", ожидается APPROVED");
        }
        if (LocalDate.now().isBefore(this.endDate)) {
            throw new IllegalStateException(
                "Нельзя завершить до даты окончания (" + this.endDate + ")");
        }
        this.status = TripStatus.COMPLETED;
    }

    public TripStatus getStatus() { return status; }
    public String getDestination() { return destination; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public String getRejectionReason() { return rejectionReason; }
}