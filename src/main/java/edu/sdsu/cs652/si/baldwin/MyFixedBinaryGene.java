package edu.sdsu.cs652.si.baldwin;

import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.FixedBinaryGene;

public class MyFixedBinaryGene extends FixedBinaryGene {

	private static final long serialVersionUID = 1L;

	public MyFixedBinaryGene(Configuration a_config, int a_length)
			throws InvalidConfigurationException {
		super(a_config, a_length);
	}


	public MyFixedBinaryGene(Configuration a_config, FixedBinaryGene a_toCopy)
			throws InvalidConfigurationException {
		super(a_config, a_toCopy);
	}


	public int size() {
		return getLength();
	}

	public Object clone() {
		try {
			return new MyFixedBinaryGene(getConfiguration(), this);
		} catch (InvalidConfigurationException iex) {
			throw new IllegalStateException(iex.getMessage());
		}
	}
	
	protected Gene newGeneInternal() {
	    try {
	    	MyFixedBinaryGene result = new MyFixedBinaryGene(getConfiguration(), getLength());
	      return result;
	    }
	    catch (InvalidConfigurationException iex) {
	      throw new IllegalStateException(iex.getMessage());
	    }
	  }
	
	public MyFixedBinaryGene substring(final int a_from, final int a_to) {
	    try {
	      int len = checkSubLength(a_from, a_to);
	      MyFixedBinaryGene substring = new MyFixedBinaryGene(getConfiguration(), len);
	      for (int i = a_from; i <= a_to; i++) {
	        substring.setUnchecked(i - a_from, getUnchecked(i));
	      }
	      return substring;
	    }
	    catch (InvalidConfigurationException iex) {
	      throw new IllegalStateException(iex.getMessage());
	    }
	  }

}
