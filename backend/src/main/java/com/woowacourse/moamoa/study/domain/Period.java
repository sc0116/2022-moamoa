package com.woowacourse.moamoa.study.domain;

import com.woowacourse.moamoa.study.domain.exception.InvalidPeriodException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Period {

    private LocalDate enrollmentEndDate;

    @Column(nullable = false)
    private LocalDate startDate;

    private LocalDate endDate;

    public Period() {
    }

    public Period(final LocalDate enrollmentEndDate, final LocalDate startDate, final LocalDate endDate) {
        if ((endDate != null && startDate.isAfter(endDate)) || (enrollmentEndDate != null && endDate != null && enrollmentEndDate.isAfter(endDate))) {
            throw new InvalidPeriodException();
        }
        this.enrollmentEndDate = enrollmentEndDate;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isBefore(final LocalDateTime createAt) {
        return startDate.isBefore(createAt.toLocalDate()) || (enrollmentEndDate != null && enrollmentEndDate.isBefore(createAt.toLocalDate()));
    }

    public boolean isBeforeThanStartDate(final LocalDate reviewCreatedDate) {
        return startDate.isAfter(reviewCreatedDate);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Period period = (Period) o;
        return Objects.equals(enrollmentEndDate, period.enrollmentEndDate) && Objects.equals(startDate,
                period.startDate) && Objects.equals(endDate, period.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enrollmentEndDate, startDate, endDate);
    }
}
