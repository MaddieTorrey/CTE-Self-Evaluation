import java.util.*;

public class CTEAssessment {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int score = 0;
        boolean playedContactSports = false;

        System.out.println("=== CTE Symptom Self-Assessment Tool ===");
        System.out.println("Answer the following questions with 'yes' or 'no', or on a scale from 1 to 10 as prompted.\n");

        // Yes/No Questions
        String[] yesNoQuestions = {
            "Have you played contact sports (e.g., football, hockey, boxing) for multiple years?", 
            "Do you have memory loss (especially recent events)?", 
            "Do you have trouble focusing or concentrating?", 
            "Do you struggle with making decisions or controlling impulses?", 
            "Have you experienced personality changes (irritability, aggression)?", 
            "Do you experience mood swings or outbursts?", 
            "Do you have feelings of depression or hopelessness?", 
            "Have you ever had suicidal thoughts or tendencies?", 
            "Do you have balance problems or unsteady walking?", 
            "Do you experience tremors or shaking in limbs?", 
            "Have you had persistent or recurring headaches?"
        };

        // Scale Questions
        String[] scaleQuestions = {
            "On a scale of 1 to 10, how severe is your anxiety?", 
            "On a scale of 1 to 10, how often do you experience dizziness?", 
            "On a scale of 1 to 10, how would you rate your judgment issues?", 
            "On a scale of 1 to 10, how bad is your speech difficulty?", 
            "On a scale of 1 to 10, how often do you feel emotionally unstable?", 
            "On a scale of 1 to 10, how severe is your chronic pain (e.g., neck, back, joints)?", 
            "On a scale of 1 to 10, how often do you experience headaches or migraines?", 
            "On a scale of 1 to 10, how sensitive are you to light or sound?", 
            "On a scale of 1 to 10, how disrupted is your sleep due to pain?", 
            "On a scale of 1 to 10, how reliant are you on medication for managing physical or mental symptoms?"
        };

        // Weights for Yes/No Questions (fixed weights array with 11 entries to match yesNoQuestions)
        int[] yesNoWeights = {2, 2, 2, 2, 2, 2, 2, 3, 2, 2, 2}; // Added one more weight

        // Handle Yes/No questions
        for (int i = 0; i < yesNoQuestions.length; i++) {
            boolean answer = askYesNo(scanner, yesNoQuestions[i]);
            score += answer ? yesNoWeights[i] : 0;
            // Track if the user played contact sports
            if (yesNoQuestions[i].toLowerCase().contains("played contact sports") && answer) {
                playedContactSports = true;
            }
        }

        // Handle Scale Questions
        for (String question : scaleQuestions) {
            int scaleScore = askScale(scanner, question);
            score += scaleScore;
        }

        // Results interpretation
        System.out.println("\n--- Assessment Results ---");
        System.out.println("Symptom Score: " + score);

        // Adjusted thresholds for risk
        if (score > 40) {
            System.out.println("⚠️ Moderate Risk: Your symptoms suggest a moderate likelihood of CTE. Please seek professional medical help.");
        } else if (score >= 25) {
            System.out.println("🟡 Mild Risk: Some symptoms are present. Monitor closely and consider a check-up.");
        } else {
            System.out.println("🟢 Low Risk: Few symptoms present. Stay healthy and aware.");
        }

        // Diagnosis summary based on statistics
        System.out.println("\n--- Statistical Diagnosis Summary ---");
        if (playedContactSports) {
            System.out.println("You indicated a history of playing contact sports, which is a known risk factor.");
            System.out.println("📊 Studies show that: ");
            System.out.println("- 91.7% of studied former NFL players had CTE.");
            System.out.println("- 41.4% of young contact sport athletes who died before 30 had CTE.");
            System.out.println("- Living players: ~34% of former NFL players believe they may have CTE.");
        } else {
            System.out.println("You did not indicate a history of playing contact sports.");
            System.out.println("📊 In the general population, CTE incidence is believed to be less than 1%.");
        }

        System.out.println("\n⚠️ Note: CTE can only be definitively diagnosed after death via brain autopsy.");
        System.out.println("This tool only estimates **risk** based on symptoms and known risk factors.");
        System.out.println("If you're experiencing severe or persistent symptoms, please contact a medical professional.");

        double probability = calculateProbability(playedContactSports, score);
        System.out.println("\n================= CTE RISK ESTIMATE =================");
        System.out.printf("📈 Estimated Probability of CTE: %.1f%%\n", probability * 100);
        System.out.println("⚠️ This is a rough estimate for educational purposes only and not a diagnosis.");
        System.out.println("🧠 CTE can only be confirmed via post-mortem brain analysis.");

        scanner.close();
    }

    private static boolean askYesNo(Scanner scanner, String question) {
        while (true) {
            System.out.print(question + " (yes/no): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("yes") || input.equals("y")) return true;
            else if (input.equals("no") || input.equals("n")) return false;
            else System.out.println("Please answer with 'yes' or 'no'.");
        }
    }

    private static int askScale(Scanner scanner, String question) {
        while (true) {
            System.out.print(question + " (1-10): ");
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value >= 1 && value <= 10) return value;
                else System.out.println("Please enter a number between 1 and 10.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private static double calculateProbability(boolean playedContactSports, int score) {
        if (playedContactSports) {
            if (score > 45) return 0.30; // Lowered high-risk probability
            else if (score >= 30) return 0.20; // Lowered moderate-risk probability
            else if (score >= 15) return 0.10;
            else return 0.05;
        } else {
            if (score > 45) return 0.05; // Lowered high-risk probability for non-contact athletes
            else if (score >= 30) return 0.03; // Lowered moderate-risk probability for non-contact athletes
            else if (score >= 15) return 0.01;
            else return 0.001;
        }
    }
}

