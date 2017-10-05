package commexercise;

public class PiCalculator {

  public static long picalc(long rounds) {
    double x, y, pi; 
    long score=0;

    for (long n = 0; n < rounds; n++)  {
      x= (2*Math.random()) - 1.0;
      y = (2*Math.random()) - 1.0;
      if ((Math.pow(x,2) + Math.pow(y,2)) <= 1.0) {
        score++;
      }
    }
    return score;
  } 
  
}
