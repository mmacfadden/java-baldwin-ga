package edu.sdsu.cs652.si.baldwin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jgap.Configuration;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.ICloneHandler;
import org.jgap.InvalidConfigurationException;
import org.jgap.NaturalSelectorExt;
import org.jgap.Population;
import org.jgap.RandomGenerator;
import org.jgap.util.CloneException;
import org.jgap.util.ICloneable;


public class RankProportionalSelector extends NaturalSelectorExt implements ICloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Keeps track of the total number of slots that are in use on the roulette
	 * wheel. This is equal to the combined fitness values of all Chromosome
	 * instances that have been added to this wheel.
	 */
	private int totlaSlots;

	private List<IChromosome> chromosomes;
	private List<Integer> slotCounts;

	public RankProportionalSelector() throws InvalidConfigurationException {
		this(Genotype.getStaticConfiguration());
	}

	/**
	 * @param a_config
	 *            the configuration to use
	 * 
	 * @throws InvalidConfigurationException
	 * 
	 * @author Klaus Meffert
	 * @since 3.0
	 */
	public RankProportionalSelector(Configuration a_config)
			throws InvalidConfigurationException {
		super(a_config);
		this.chromosomes = new ArrayList<IChromosome>();
		this.slotCounts = new ArrayList<Integer>();
	}

	/**
	 * Add a chromosome instance to this selector's working pool of chromosomes.
	 * 
	 * @param a_chromosomeToAdd
	 *            the chromosom to add to the pool
	 * 
	 * @author Neil Rotstan
	 * @author Klaus Meffert
	 * @since 1.0
	 */
	protected synchronized void add(final IChromosome a_chromosomeToAdd) {
		this.chromosomes.add(a_chromosomeToAdd);
	}

	/**
	 * Select a given number of Chromosomes from the pool that will move on to
	 * the next generation population. This selection should be guided by the
	 * fitness values, but fitness should be treated as a statistical
	 * probability of survival, not as the sole determining factor. In other
	 * words, Chromosomes with higher fitness values should be more likely to be
	 * selected than those with lower fitness values, but it should not be
	 * guaranteed.
	 * 
	 * @param a_howManyToSelect
	 *            the number of Chromosomes to select
	 * @param a_to_pop
	 *            the population the Chromosomes will be added to
	 * 
	 * @author Neil Rotstan
	 * @author Klaus Meffert
	 * @since 1.0
	 */
	public synchronized void selectChromosomes(int a_howManyToSelect, Population a_to_pop) {
		RandomGenerator generator = getConfiguration().getRandomGenerator();
		
		Collections.sort(chromosomes, new Comparator<IChromosome>() {
			public int compare(IChromosome o1, IChromosome o2) {
				if (o1.getFitnessValueDirectly() < o2.getFitnessValueDirectly()) {
					return -1;
				} else if (o1.getFitnessValueDirectly() > o2
						.getFitnessValueDirectly()) {
					return 1;
				} else {
					return 0;
				}
			}
		});

		this.totlaSlots = 0;
		for (int i = 1; i <= chromosomes.size(); i++) {
			this.slotCounts.add(i);
			// We're also keeping track of the total number of slots,
			// which is the sum of all the counter values.
			// ------------------------------------------------------
			this.totlaSlots += i;
		}
		// To select each chromosome, we just "spin" the wheel and grab
		// whichever chromosome it lands on.
		// ------------------------------------------------------------
		IChromosome selectedChromosome;
		for (int i = 0; i < a_howManyToSelect; i++) {
			selectedChromosome = selectChromosome(generator);
			selectedChromosome.setIsSelectedForNextGeneration(true);
			if (a_to_pop.contains(selectedChromosome)) {
				ICloneHandler cloner = getConfiguration().getJGAPFactory().getCloneHandlerFor(selectedChromosome, null);
				if (cloner != null) {
					try {
						IChromosome cloned = (IChromosome) cloner.perform(selectedChromosome, null, null);
						a_to_pop.addChromosome(cloned);
						if (m_monitorActive) {
							cloned.setUniqueIDTemplate(
									selectedChromosome.getUniqueID(), 1);
						}
					} catch (Exception ex) {
						ex.printStackTrace();
						a_to_pop.addChromosome(selectedChromosome);
					}
				} else {
					a_to_pop.addChromosome(selectedChromosome);
					if (m_monitorActive) {
						selectedChromosome.setUniqueIDTemplate(
								selectedChromosome.getUniqueID(), 1);
					}
				}
			} else {
				a_to_pop.addChromosome(selectedChromosome);
				if (m_monitorActive) {
					selectedChromosome.setUniqueIDTemplate("new", 1);
				}
			}
		}
	}

	/**
	 * This method "spins" the wheel and returns the Chromosome that is
	 * "landed upon." Each time a chromosome is selected, one instance of it is
	 * removed from the wheel so that it cannot be selected again.
	 * 
	 * @param a_generator
	 *            the random number generator to be used during the spinning
	 *            process
	 * @param a_fitnessValues
	 *            an array of fitness values of the respective Chromosomes
	 * @param a_counterValues
	 *            an array of total counter values of the respective Chromosomes
	 * @param a_chromosomes
	 *            the respective Chromosome instances from which selection is to
	 *            occur
	 * @return selected Chromosome from the roulette wheel
	 * 
	 * @author Neil Rotstan
	 * @author Klaus Meffert
	 * @since 1.0
	 */
	private IChromosome selectChromosome(RandomGenerator generator) {
		int selectedSlot = generator.nextInt(this.totlaSlots);
		IChromosome result = null;
		int currentSlot = 0;
		for (int i = 0; i < this.slotCounts.size() && result == null; i++) {
			if (selectedSlot <= currentSlot) {
				result = this.chromosomes.get(i);
				this.chromosomes.remove(i);
				this.totlaSlots = this.totlaSlots - this.slotCounts.remove(i);
				return result;
			} else {
				currentSlot += this.slotCounts.get(i);
			}
		}
		return this.chromosomes.get(this.chromosomes.size() - 1);
	}

	/**
	 * Empty out the working pool of Chromosomes.
	 * 
	 * @author Neil Rotstan
	 * @since 1.0
	 */
	public synchronized void empty() {
		this.chromosomes.clear();
		this.slotCounts.clear();
	}

	/**
	 * @return always false as some Chromosome's could be returnd multiple times
	 * 
	 * @author Klaus Meffert
	 * @since 2.0
	 */
	public boolean returnsUniqueChromosomes() {
		return true;
	}

	/**
	 * Not supported by this selector! Please do not use it.
	 * 
	 * @param a_doublettesAllowed
	 *            do not use
	 * 
	 * @author Klaus Meffert
	 * @since 2.0
	 */
	public void setDoubletteChromosomesAllowed(final boolean a_doublettesAllowed) {
		throw new IllegalStateException("Weighted roulette selector does not"
				+ " support this parameter," + " please do not use it!");
	}

	/**
	 * @return TRUE: doublette chromosomes allowed to be added by the selector
	 * 
	 * @author Klaus Meffert
	 * @since 2.0
	 */
	public boolean getDoubletteChromosomesAllowed() {
		return true;
	}

	/**
	 * @return deep clone of this instance
	 * 
	 * @author Klaus Meffert
	 * @since 3.2
	 */
	public Object clone() {
		try {
			RankProportionalSelector result = new RankProportionalSelector(
					getConfiguration());
			result.chromosomes = new ArrayList<IChromosome>(this.chromosomes);
			result.slotCounts = new ArrayList<Integer>(this.slotCounts);
			return result;
		} catch (InvalidConfigurationException iex) {
			throw new CloneException(iex);
		}
	}

	public boolean equals(Object o) {
		RankProportionalSelector other = (RankProportionalSelector) o;
		if (other == null) {
			return false;
		}
		
		return (this.chromosomes.equals(other.chromosomes)) &&
				(this.slotCounts.equals(other.slotCounts));
	}
}
