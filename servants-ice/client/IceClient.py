import Ice
import sys
import Objects
import random

class Client(Ice.Application):
    def run(self, argv):
        with Ice.initialize(argv) as communicator:
            host = "127.0.0.2"
            port = 10000

            if len(argv) < 2 or (len(argv) > 2 and not argv[2].isdigit()):
                print(f"Usage: python {argv[0]} <objects to test: p, r, s, c> <num of repeats>")
                return 1
            
            objects = argv[1].lower()
            repeats = 1
            if len(argv) > 2:
                repeats = int(argv[2])

            try:
                checker_prx = communicator.stringToProxy(f"shared/checker:tcp -h {host} -p {port} -z")
                palindrome_checker = Objects.PalindromeCheckerPrx.checkedCast(checker_prx)

                reverser_prx = communicator.stringToProxy(f"shared/reverser:tcp -h {host} -p {port} -z")
                string_reverser = Objects.StringReverserPrx.checkedCast(reverser_prx)

                stack_prx = communicator.stringToProxy(f"dedicated/stack:tcp -h {host} -p {port} -z")
                stack = Objects.StackPrx.uncheckedCast(stack_prx)

                counter_prx = communicator.stringToProxy(f"dedicated/counter:tcp -h {host} -p {port} -z")
                counter = Objects.CounterPrx.uncheckedCast(counter_prx)
            except Exception as e:
                sys.stderr.write(f"Error: {e}\n")
                return 1

            if not palindrome_checker or not string_reverser or not stack or not counter:
                raise RuntimeError("Invalid proxy")
            
            for _ in range(repeats):
                try:
                    if "p" in objects:
                        rand_dig1, rand_dig2 = random.randint(3, 4), random.randint(3, 4)
                        number = int("12"+str(rand_dig1)+"565"+str(rand_dig2)+"21")
                        if palindrome_checker.isPalindrome(number):
                            print(f"'Number {number}' is a palindrome")
                        else:
                            print(f"'Number {number}' is not a palindrome")
                    if "r" in objects:
                        text = "hello world and rest of the universe"
                        print(f"The reversed string {text} is: " + string_reverser.reverse(text))
                    if "s" in objects:
                        if stack.isEmpty():
                            stack.push(1)
                            stack.push(1)
                        stack.push(sum(stack.topN(2)))
                        print("The top 3 elements of the stack are: " + str(stack.topN(3)))
                    if "c" in objects:
                        print("The counter value is: " + str(counter.getValue()))
                        counter.increment()
                        print("The counter value after incrementation is: " + str(counter.getValue()))
                except Exception as e:
                    sys.stderr.write(f"Error: {e}\n")
                    return 1
            
def main():
    app = Client()
    sys.exit(app.main(sys.argv))

if __name__ == '__main__':
    main()
