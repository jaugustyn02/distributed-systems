package server;

import Objects.PalindromeChecker;
import com.zeroc.Ice.Current;

public class PalindromeCheckerI implements PalindromeChecker{
    @Override
    public boolean isPalindrome(long number, Current current) {
        long reversedNumber = 0;
        long temp = number;
        while (temp != 0) {
            long digit = temp % 10;
            reversedNumber = reversedNumber * 10 + digit;
            temp /= 10;
        }
        return number == reversedNumber;
    }
}
