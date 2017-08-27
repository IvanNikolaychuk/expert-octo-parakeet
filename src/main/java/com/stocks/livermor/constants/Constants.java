package com.stocks.livermor.constants;

import java.util.Calendar;
import java.util.Date;

public class Constants {
    public static final int BASIC_CHANGE_PERCENTAGE = 1;
    public static final int BASIC_CHANGE_POINT = 3;
    public static final Date NULL_DATE = getNullDate();

    public static Date getNullDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 1995);
        return calendar.getTime();
    }

    public enum Rule {
        // Восходящий тренд
        _6aa("During up trend reaction 6+ points, but (last_up_trend - 3 points < last_up_trend_pivot_point) and no down trend between them"),
        _6a("During up trend reaction 6+ points"),

        // Нисходящий тренд
        _6cc("During down trend rally 6+ points, but (last_down_trend + 3 points > last_down_trend_pivot_point) and no up trend between them"),
        _6c("During down trend rally 6+ points"),

        // Естественная реакция
        _5b("During natural/secondary reaction there is 3+ points reaction from last pivot point in natural reaction"),
        _6d("During natural reaction there is 6+ points rally"),
        _6g("During natural/secondary reaction there is 6+ points rally, but price is lower than last in natural rally"),
        _11a("During natural reaction there is a price higher than last in upper trend"),
        _6b3("During natural reaction there is a price lower than last in down trend"),

        // Естественное ралли
        _5a("During natural/secondary rally there is 3+ points rally from last pivot point in natural rally"),
        _6b("During natural rally there is 6+ points reaction"),
        _6h("During natural/secondary reaction there is 6+ points reaction, but price is higher than last in natural reaction"),
        _6d3("During natural rally there is a price higher than last in upper trend"),
        _11b("During natural rally there is a price lower than last in down trend"),

        // Вторичное ралли
        _6g3("During secondary rally price is higher than last in natural rally"),

        // Вторичная реакция
        _6h3("During secondary reaction price is lower than last in natural reaction"),

        _12_rally("Continue natural rally. Last price is higher than previous."),
        _12_reaction("Continue natural reaction. Last price is lower than previous."),
        _12_secondary_reaction("Continue secondary reaction. Last price is lower than previous (but not lower than last in natural reaction)"),
        _12_secondary_rally("Continue secondary rally. Last price is higher than previous (but not higher than last in natural rally)"),
        _12_upper("Continue up trend. Last price is higher than previous."),
        _12_down("Continue down trend. Last price is lower than previous.");

        String explanation;

        Rule(String explanation) {
            this.explanation = explanation;
        }

        public String getExplanation() {
            return explanation;
        }
    }
}
