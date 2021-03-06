/*
 * Encog(tm) Core v3.2 - Java Version
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 
 * Copyright 2008-2012 Heaton Research, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *   
 * For more information on Heaton Research copyrights, licenses 
 * and trademarks visit:
 * http://www.heatonresearch.com/copyright
 */
package org.encog.neural.neat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.encog.mathutil.randomize.RangeRandomizer;
import org.encog.ml.ea.genome.Genome;
import org.encog.ml.ea.population.Population;
import org.encog.neural.neat.training.NEATGenome;
import org.encog.util.Format;

/**
 * Provides basic functionality for a species.
 */
public class NEATSpecies implements Serializable {

	/**
	 * Serial id.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The age of this species.
	 */
	private int age;

	/**
	 * The best score.
	 */
	private double bestScore;

	/**
	 * The number of generations with no improvement.
	 */
	private int gensNoImprovement;

	/**
	 * The leader.
	 */
	private NEATGenome leader;

	/**
	 * The list of genomes.
	 */
	private final List<NEATGenome> members = new ArrayList<NEATGenome>();

	/**
	 * The species id.
	 */
	private long speciesID;

	/**
	 * The owner class.
	 */
	private NEATPopulation population;

	/**
	 * The id of the leader.
	 */
	private long leaderID;

	private transient int offspringCount;
	private transient double offspringShare;

	/**
	 * Default constructor, used mainly for persistence.
	 */
	public NEATSpecies() {

	}

	/**
	 * Construct a species.
	 * 
	 * @param thePopulation
	 *            The population the species belongs to.
	 * @param theFirst
	 *            The first genome in the species.
	 * @param theSpeciesID
	 *            The species id.
	 */
	public NEATSpecies(final NEATPopulation thePopulation,
			final NEATGenome theFirst, final long theSpeciesID) {
		this.population = thePopulation;
		this.speciesID = theSpeciesID;
		this.bestScore = theFirst.getScore();
		this.gensNoImprovement = 0;
		this.age = 0;
		this.leader = theFirst;
		this.members.add(theFirst);
	}

	public double calculateShare(final boolean shouldMinimize,
			final double maxScore) {
		double total = 0;

		int count = 0;
		for (final NEATGenome genome : this.members) {
			if (!Double.isNaN(genome.getScore())
					&& !Double.isInfinite(genome.getScore())) {
				double s;
				if (shouldMinimize) {
					s = maxScore - genome.getScore();
				} else {
					s = genome.getScore();
				}
				total += s;
				count++;
			}
		}

		if (count == 0) {
			this.offspringShare = 0;
		} else {
			this.offspringShare = total / count;
		}

		return this.offspringShare;
	}

	/**
	 * Choose a parent to mate. Choose from the population, determined by the
	 * survival rate. From this pool, a random parent is chosen.
	 * 
	 * @return The parent.
	 */
	public Genome chooseParent(final Random rnd) {
		Genome result;

		// If there is a single member, then choose that one.
		if (this.members.size() == 1) {
			result = this.members.get(0);
		} else {
			// If there are many, then choose the population based on survival
			// rate and select a random genome.
			final int theOne = (int) RangeRandomizer.randomize(rnd, 0,
					getEliteSize());
			result = this.members.get(theOne);
		}

		return result;
	}

	/**
	 * @return The age of this species.
	 */
	public int getAge() {
		return this.age;
	}

	/**
	 * @return The best score for this species.
	 */
	public double getBestScore() {
		return this.bestScore;
	}

	public int getEliteSize() {
		return (int) (this.population.getSurvivalRate() * this.members.size()) + 1;
	}

	/**
	 * @return The number of generations with no improvement.
	 */
	public int getGensNoImprovement() {
		return this.gensNoImprovement;
	}

	/**
	 * @return THe leader of this species.
	 */
	public NEATGenome getLeader() {
		return this.leader;
	}

	/**
	 * @return The members of this species.
	 */
	public List<NEATGenome> getMembers() {
		return this.members;
	}

	/**
	 * @return the offspringCount
	 */
	public int getOffspringCount() {
		return this.offspringCount;
	}

	/**
	 * @return the offspringShare
	 */
	public double getOffspringShare() {
		return this.offspringShare;
	}

	/**
	 * @return The population that this species belongs to.
	 */
	public Population getPopulation() {
		return this.population;
	}

	/**
	 * @return The species ID.
	 */
	public long getSpeciesID() {
		return this.speciesID;
	}

	/**
	 * @return the leaderID
	 */
	public long getTempLeaderID() {
		return this.leaderID;
	}

	/**
	 * Purge all members, increase age by one and count the number of
	 * generations with no improvement.
	 */
	public void purge() {
		this.members.clear();
		if (this.leader != null) {
			this.members.add(this.leader);
		}
		this.age++;
		this.gensNoImprovement++;
		this.offspringCount = 0;
		this.offspringShare = 0;
	}

	/**
	 * Set the age of this species.
	 * 
	 * @param theAge
	 *            The age of this species.
	 */
	public void setAge(final int theAge) {
		this.age = theAge;
	}

	/**
	 * Set the best score.
	 * 
	 * @param theBestScore
	 *            The best score.
	 */
	public void setBestScore(final double theBestScore) {
		this.bestScore = theBestScore;
	}

	/**
	 * Set the number of generations with no improvement.
	 * 
	 * @param theGensNoImprovement
	 *            The number of generations.
	 */
	public void setGensNoImprovement(final int theGensNoImprovement) {
		this.gensNoImprovement = theGensNoImprovement;
	}

	/**
	 * Set the leader.
	 * 
	 * @param theLeader
	 *            The new leader.
	 */
	public void setLeader(final NEATGenome theLeader) {
		this.leader = theLeader;
		this.leaderID = theLeader.getGenomeID();
	}

	/**
	 * @param offspringCount
	 *            the offspringCount to set
	 */
	public void setOffspringCount(final int offspringCount) {
		this.offspringCount = offspringCount;
	}

	/**
	 * @param thePopulation
	 *            the population to set
	 */
	public void setPopulation(final NEATPopulation thePopulation) {
		this.population = thePopulation;
	}

	/**
	 * Set the species id.
	 * 
	 * @param i
	 *            The new species id.
	 */
	public void setSpeciesID(final int i) {
		this.speciesID = i;
	}

	/**
	 * Set the leader id. This value is not persisted, it is used only for
	 * loading.
	 * 
	 * @param theLeaderID
	 *            the leaderID to set
	 */
	public void setTempLeaderID(final long theLeaderID) {
		this.leaderID = theLeaderID;
	}

	@Override
	public String toString() {
		final StringBuilder result = new StringBuilder();
		result.append("[NEATSpecies: score=");
		result.append(Format.formatDouble(getBestScore(), 2));
		result.append(", members=");
		result.append(this.members.size());
		result.append(", age=");
		result.append(this.age);
		result.append(", no_improv=");
		result.append(this.gensNoImprovement);
		result.append(", share=");
		result.append(this.offspringShare);
		result.append(", offspring count=");
		result.append(this.offspringShare);
		result.append(", elite=");
		result.append(getEliteSize());
		result.append("]");
		return result.toString();
	}

}
