package timeseries.models;

import timeseries.TimeSeries;

/**
 * An Autoregressive Integrated Moving Average model.
 * @author Jacob Rachiele
 *
 */
public final class Arima {
	
	private final TimeSeries observations;
	// The number of parameters, degree of differencing, and constant flag.
	private final ModelOrder order;
	
	private Arima(final TimeSeries observations, final ModelOrder order) {
		this.observations = observations.copy();
		this.order = order.copy();
		double[] initialParameters = setInitialParameters();
	}
	
	private final double[] setInitialParameters() {
	  // Set initial constant to the mean and all other parameters to zero.
	  double[] initParams = new double[order.sumARMA() + order.constant];
	  if (order.constant == 1) {
	    initParams[initParams.length - 1] = observations.mean();
	  }
	  return initParams;
	}
	
	/**
	 * The order of an ARIMA model, consisting of the number of autoregressive and moving average parameters,
	 * along with the degree of differencing and whether or not a constant is in the model.
	 * @author Jacob Rachiele
	 *
	 */
	public static final class ModelOrder {
		private final int p;
		private final int d;
		private final int q;
		private final int P;
		private final int D;
		private final int Q;
		private final int constant;
		
		public ModelOrder(final int p, final int d, final int q, final int P, final int D, final int Q, 
				final boolean constant) {
			this.p = p;
			this.d = d;
			this.q = q;
			this.P = P;
			this.D = D;
			this.Q = Q;
			this.constant = (constant == true)? 1 : 0;
		}
		
		// This returns the total number of nonseasonal and seasonal ARMA parameters.
		private final int sumARMA() {
		  return this.p + this.q + this.P + this.Q;
		}
		
    public final ModelOrder copy() {
      return new ModelOrder(this);
    }
		
		private ModelOrder(final ModelOrder original) {
			this.p = original.p;
			this.d = original.d;
			this.q = original.q;
			this.P = original.P;
			this.D = original.D;
			this.Q = original.Q;
			this.constant = original.constant;
		}
	}

}