package pt.codered.afk_47.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.scoreboard.*;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScoreboardUtils {
    public static List<String> getSidebarLines() {
        List<String> lines = new ArrayList<>();
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.world == null) return lines;

        Scoreboard scoreboard = client.world.getScoreboard();

        ScoreboardObjective objective = scoreboard.getObjectiveForSlot(ScoreboardDisplaySlot.SIDEBAR);

        if (objective == null) return lines;

        List<ScoreboardEntry> scores = new ArrayList<>(scoreboard.getScoreboardEntries(objective));

        scores.sort((s1, s2) -> Integer.compare(s2.value(), s1.value()));

        for (ScoreboardEntry score : scores) {
            String owner = score.owner();

            Team team = scoreboard.getScoreHolderTeam(owner);

            if (team != null) {
                Text prefix = team.getPrefix();
                Text suffix = team.getSuffix();

                String lineText = prefix.getString() + suffix.getString();
                lines.add(lineText);
            }
        }
        return lines;
    }

    public static String getLocationString() {
        List<String> sidebar = getSidebarLines();

        for (String line : sidebar) {
            String cleanLine = line.replaceAll("§[0-9a-fk-or]", "");

            if (cleanLine.contains("⏣")) {
                return cleanLine.replace("⏣", "").trim();
            }
        }
        return "Unknown Location";
    }

    private static final Pattern TIME_PATTERN = Pattern.compile("(\\d{1,2}):(\\d{2})(am|pm)");

    public static String getSkyblockTime() {

        for (String line : getSidebarLines()) {
            String cleanLine = line.replaceAll("§[0-9a-fk-or]", "").trim();

            Matcher matcher = TIME_PATTERN.matcher(cleanLine);

            if (matcher.find()) {
                return matcher.group(1);
            }
        }

        return "Unknown Time";
    }

    public static int getSkyblockTime24h() {
        for (String line : getSidebarLines()) {
            Matcher matcher = TIME_PATTERN.matcher(line);

            if (matcher.find()) {
                try {
                    return getHour(matcher);
                } catch (NumberFormatException ignored) {
                    //TODO: log error
                }
            }
        }
        return -1;
    }

    private static int getHour(Matcher matcher) {
        int hour = Integer.parseInt(matcher.group(1));
        String period = matcher.group(3);

        if (period.equalsIgnoreCase("pm") && hour != 12) {
            hour += 12;
        } else if (period.equalsIgnoreCase("am") && hour == 12) {
            hour = 0;
        }
        return hour;
    }
}
