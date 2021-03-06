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
package org.encog.neural.neat.training.opp.links;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.encog.neural.neat.training.NEATGenome;
import org.encog.neural.neat.training.NEATLinkGene;
import org.encog.neural.neat.training.NEATTraining;

/**
 * Select a random proportion of links to mutate.
 */
public class SelectProportion implements SelectLinks {
	
	/**
	 * The portion of the links to mutate. 0.0 for none, 1.0 for all.
	 */
	private double proportion;
	
	/**
	 * The trainer.
	 */
	private NEATTraining trainer;
	
	/**
	 * {@inheritDoc}
	 */
	public SelectProportion(double theProportion) {
		this.proportion = theProportion;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(NEATTraining theTrainer) {
		this.trainer = theTrainer;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<NEATLinkGene> selectLinks(Random rnd, NEATGenome genome) {
		List<NEATLinkGene> result = new ArrayList<NEATLinkGene>();
		
		boolean mutated = false;
		
		for (final NEATLinkGene linkGene : genome.getLinksChromosome()) {
			if (rnd.nextDouble() < this.proportion) {
				mutated = true;
				result.add(linkGene);	
			}
		}
		
		if( !mutated ) {
			int idx = rnd.nextInt(genome.getLinksChromosome().size());
			NEATLinkGene linkGene  = genome.getLinksChromosome().get(idx);
			result.add(linkGene);	
		}
		
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NEATTraining getTrainer() {
		return trainer;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("[");
		result.append(this.getClass().getSimpleName());
		result.append(":proportion=");
		result.append(this.proportion);
		result.append("]");
		return result.toString();
	}
	
	
}
