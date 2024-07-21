// 6510405521 Thatpong Wongchaita

import java.util.Random;

public class App {
  public static void main(String[] args) throws Exception {
    GumballMachine gumballMachine = new GumballMachine(5);

    System.out.println(gumballMachine);

    gumballMachine.insertQuarter();
    gumballMachine.turnsCrank();

    System.out.println(gumballMachine);

    gumballMachine.insertQuarter();
    gumballMachine.chooseFlavor("Mango");
    gumballMachine.chooseFlavor("Orange");
    gumballMachine.turnsCrank();
    gumballMachine.insertQuarter();
    gumballMachine.turnsCrank();

    System.out.println(gumballMachine);
  }
}

interface State {

  public void insertQuarter();

  public void ejectQuarter();

  public void turnsCrank();

  public void dispenseGumball();

}

class GumballMachine {

  private State soldOutState;
  private State noQuarterState;
  private State hasQuarterState;
  private State soldState;
  private State winnerState;

  private State state = soldOutState;
  private int count = 0;
  private String gumballFlavor = "";

  public GumballMachine(int numberGumballs) {
    this.soldOutState = new OutOfGumballs(this);
    this.noQuarterState = new NoQuarter(this);
    this.hasQuarterState = new HasQuarter(this);
    this.soldState = new GumballSold(this);
    this.winnerState = new Winner(this);

    this.count = numberGumballs;
    if (numberGumballs > 0) {
      this.state = noQuarterState;
    }
  }

  public void insertQuarter() {
    state.insertQuarter();
  }

  public void chooseFlavor(String flavor) {
    this.gumballFlavor = flavor;
    System.out.println("You have chosen the flavor " + this.gumballFlavor);
  }

  public void ejectQuarter() {
    state.ejectQuarter();
  }

  public void turnsCrank() {
    state.turnsCrank();

    state.dispenseGumball();
  }

  public void setState(State state) {
    this.state = state;
  }

  void releaseBall() {
    System.out.println("A " + this.gumballFlavor + " gumball comes rolling out the slot...");
    if (this.count != 0) {
      this.count -= 1;
    }
  }

  public State getNoQuarterState() {
    return this.noQuarterState;
  }

  public State getSoldOutState() {
    return this.soldOutState;
  }

  public State getHasQuarterState() {
    return this.hasQuarterState;
  }

  public State getSoldState() {
    return this.soldState;
  }

  public State getWinnerState() {
    return this.winnerState;
  }

  public int getCount() {
    return this.count;
  }

  public String getFlavor() {
    return this.gumballFlavor;
  }

  @Override
  public String toString() {
    return "GumballMachine [state=" + state + ", count=" + count + "]";
  }

}

class NoQuarter implements State {

  private GumballMachine gumballMachine;

  public NoQuarter(GumballMachine gumballMachine) {
    this.gumballMachine = gumballMachine;
  }

  public void insertQuarter() {
    System.out.println("You inserted a quarter");
    this.gumballMachine.setState(this.gumballMachine.getHasQuarterState());
  }

  public void ejectQuarter() {
    System.out.println("You haven't inserted a quarter");
  }

  public void chooseFlavor(String flavor) {

  }

  public void turnsCrank() {
    System.out.println("You turned but there's no quarter");
  }

  public void dispenseGumball() {
    System.out.println("You need to pay first");
  }
}

class HasQuarter implements State {

  Random randomWinner = new Random(System.currentTimeMillis());
  private GumballMachine gumballMachine;

  public HasQuarter(GumballMachine gumballMachine) {
    this.gumballMachine = gumballMachine;
  }

  public void insertQuarter() {
    System.out.println("You can't insert another quarter");
  }

  public void ejectQuarter() {
    System.out.println("Quarter returned");
    this.gumballMachine.setState(this.gumballMachine.getNoQuarterState());
  }

  public void turnsCrank() {

    if (this.
     

    }
    
    System.out.println("You turned...");
    int winner = randomWinner.nextInt(10);

    if ((winner == 0) && (this.gumballMachine.getCount() > 1)) {
      this.gumballMachine.setState(this.gumballMachine.getWinnerState());
    } else {
      this.gumballMachine.setState(this.gumballMachine.getSoldState());
    }
  }

  public void dispenseGumball() {
    System.out.println("No gumball dispensed");
  }
}

class GumballSold implements State {

  private GumballMachine gumballMachine;

  public GumballSold(GumballMachine gumballMachine) {
    this.gumballMachine = gumballMachine;
  }

  public void insertQuarter() {
    System.out.println("Please wait, we're already giving you a gumball");
  }

  public void ejectQuarter() {
    System.out.println("Sorry, you already turned the crank");
  }

  public void turnsCrank() {
    System.out.println("Turning twice doesn't get you another gumball!");
  }

  public void dispenseGumball() {
    this.gumballMachine.releaseBall();
    if (this.gumballMachine.getCount() > 0) {
      this.gumballMachine.setState(this.gumballMachine.getNoQuarterState());
    } else {
      System.out.println("Oops, out of gumballs");
      this.gumballMachine.setState(this.gumballMachine.getSoldOutState());
    }
  }
}

class OutOfGumballs implements State {

  private GumballMachine gumballMachine;

  public OutOfGumballs(GumballMachine gumballMachine) {
    this.gumballMachine = gumballMachine;
  }

  public void insertQuarter() {
    System.out.println("You can't insert a quarter, the machine is sold out");
  }

  public void ejectQuarter() {
    System.out.println("You haven't inserted a quarter");
  }

  public void turnsCrank() {
    System.out.println("You turned but there are no gumballs");
  }

  public void dispenseGumball() {
    System.out.println("Oops, out of gumballs!");
  }
}

class Winner implements State {

  private GumballMachine gumballMachine;

  public Winner(GumballMachine gumballMachine) {
    this.gumballMachine = gumballMachine;
  }

  public void insertQuarter() {
    System.out.println("Please wait, we're already giving you a gumball");
  }

  public void ejectQuarter() {
    System.out.println("Sorry, you already turned the crank");
  }

  public void turnsCrank() {
    System.out.println("Turning twice doesn't get you another gumball!");
  }

  public void dispenseGumball() {
    System.out.println("YOU'RE A WINNER! you get two gumballs for your quarter");
    this.gumballMachine.releaseBall();
    if (this.gumballMachine.getCount() == 0) {
      this.gumballMachine.setState(this.gumballMachine.getSoldOutState());
    } else {
      this.gumballMachine.releaseBall();
      if (this.gumballMachine.getCount() > 0) {
        this.gumballMachine.setState(this.gumballMachine.getNoQuarterState());
      } else {
        System.out.println("Oops, out of gumballs!");
        this.gumballMachine.setState(this.gumballMachine.getSoldOutState());
      }
    }
  }

}
