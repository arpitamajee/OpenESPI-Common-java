/*
 * Copyright 2013, 2014 EnergyOS.org
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.energyos.espi.common.service;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import org.energyos.espi.common.domain.IntervalBlock;
import org.energyos.espi.common.domain.MeterReading;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.repositories.IntervalBlockRepository;
import org.energyos.espi.common.utils.EntryTypeIterator;
import org.energyos.espi.common.utils.ExportFilter;

public interface IntervalBlockService {
	// TODO: likely deprecated
	List<IntervalBlock> findAllByMeterReadingId(Long meterReadingId);

	String feedFor(List<IntervalBlock> intervalBlocks);

	IntervalBlock findByURI(String uri);

	String entryFor(IntervalBlock intervalBlock);

	void associateByUUID(MeterReading meterReading, UUID uuid);

	List<IntervalBlock> findAllByMeterReading(MeterReading meterReading);

	// persistence management services
	public void setRepository(IntervalBlockRepository repository);

	public void persist(IntervalBlock intervalBlock);

	// accessor services

	public IntervalBlock findById(long retailCustomerId, long usagePointId,
			long meterReadingId, long intervalBlockId);

	public EntryType findEntryType(Long retailCustomerId, Long usagePointId,
			Long meterReadingId, Long intervalBlockId);

	public EntryTypeIterator findEntryTypeIterator(Long retailCustomerId, Long usagePointId,
			Long meterReadingId);

	public void delete(IntervalBlock intervalBlock);

	public void add(IntervalBlock intervalBlock);

	// import-exportResource services
	public IntervalBlock importResource(InputStream stream) throws Exception;

	IntervalBlock findById(long intervalBlockId);
	
	/* LH customization starts here */
	public List<IntervalBlock> findIntervalBlocksByPeriod(Long meterReadingId,
			ExportFilter ap);
	
	public IntervalBlock findByUUID(UUID uuid) ;
	
	public IntervalBlock merge(IntervalBlock existingResource);
    void flush();

}
