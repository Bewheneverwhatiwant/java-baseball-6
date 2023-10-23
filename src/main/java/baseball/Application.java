package baseball;

import camp.nextstep.edu.missionutils.Randoms;
import camp.nextstep.edu.missionutils.Console;
import java.util.Scanner;

public class Application {

    // main 안에는 하나의 메소드를 호출하는 방식으로, 유지보수가 용이하고 가독성이 좋도록 하였습니다.
    public static void main(String[] args) {
        playNumberBaseballGame();
    }

    // 먼저 숫자 야구 게임에 대한 메인 함수를 만들었습니다.
    public static void playNumberBaseballGame() {
        //Console이 private이라 액세스 불가능한 오류 발생 중
        //Console console = new Console();
        Scanner scanner = new Scanner(System.in);
        boolean isGameOver = false;

        while (!isGameOver) {
            // 컴퓨터가 숫자 세자리를 가지고 있는 입장이므로, 그에 대한 리스트 생성
            int[] computerNumbers = generateRandomNumbers();
            System.out.println("숫자 야구 게임을 시작합니다.\n");

            //int attempts = 0;
            while (true) {
                // 역시나 private으로 인한 오류 발생 중!!
                //String input = console.readLine("숫자를 입력해주세요: ");
                String input = scanner.nextLine();

                if (!isValidInput(input)) { // 하단에 구현해두었습니다.
                    throw new IllegalArgumentException("잘못된 입력입니다. 1부터 9까지 서로 다른 숫자로 이루어진 3자리 숫자를 입력하세요.");
                }

                //사용자가 제시해나갈 숫자 3자리. 하단에 메소드 위치
                int[] userNumbers = convertInputToNumbers(input);
                //결과판단. 하단에 메소드 위치
                int[] result = calculateResult(computerNumbers, userNumbers);

                //아래는 스트라이크, 볼, 낫싱에 대한 판단
                if (result[0] > 0) {
                    System.out.println(result[0] + "스트라이크 ");
                }
                if (result[1] > 0) {
                    System.out.println(result[1] + "볼 ");
                }
                if (result[0] == 0 && result[1] == 0) {
                    System.out.println("낫싱");
                }
                System.out.println();

                if (result[0] == 3) {
                    System.out.println("3개의 숫자를 모두 맞히셨습니다! 게임 종료");
                    break;
                }
            }

            //console이 private이라서 오류 발생 중!!
            //String playAgain = console.readLine("게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요: ");
            String playAgain = scanner.nextLine();
            if (playAgain.equals("2")) {
                isGameOver = true;
            }
        }

        System.out.println("게임을 종료합니다.");
    }

    // 무작위로 서로 다른 3자리 숫자를 생성하는 함수
    public static int[] generateRandomNumbers() {
        int[] numbers = new int[3];
        for (int i = 0; i < 3; i++) {
            int num;
            do {
                // 문제 요구 사항
                num = Randoms.pickNumberInRange(1, 9);
            } while (contains(numbers, num)); //하단에 메소드 구현
            numbers[i] = num;
        }
        return numbers;
    }

    // 배열에 숫자가 이미 존재하는지 확인하는 유틸리티 함수
    public static boolean contains(int[] array, int num) {
        for (int value : array) {
            if (value == num) {
                return true;
            }
        }
        return false;
    }

    // 사용자 입력이 유효한지 확인하는 함수
    public static boolean isValidInput(String input) {
        if (input.length() != 3) {
            return false;
        }
        for (char c : input.toCharArray()) {
            if (c < '1' || c > '9') {
                return false;
            }
        }
        return areAllDigitsDifferent(input);
    }

    // 입력된 숫자들이 서로 다른 숫자인지 확인하는 함수
    public static boolean areAllDigitsDifferent(String input) {
        for (int i = 0; i < input.length(); i++) {
            for (int j = i + 1; j < input.length(); j++) {
                if (input.charAt(i) == input.charAt(j)) {
                    return false;
                }
            }
        }
        return true;
    }

    // 사용자 입력을 숫자 배열로 변환하는 함수
    public static int[] convertInputToNumbers(String input) {
        int[] numbers = new int[3];
        for (int i = 0; i < 3; i++) {
            numbers[i] = input.charAt(i) - '0';
        }
        return numbers;
    }

    // 게임 결과를 계산하는 함수 (스트라이크, 볼, 낫싱)
    public static int[] calculateResult(int[] computerNumbers, int[] userNumbers) {
        int[] result = new int[3];

        for (int i = 0; i < 3; i++) {
            if (computerNumbers[i] == userNumbers[i]) {
                result[0]++; // 스트라이크
            } else if (contains(computerNumbers, userNumbers[i])) {
                result[1]++; // 볼
            }
        }

        result[2] = 3 - result[0] - result[1]; // 낫싱
        return result;
    }
}
