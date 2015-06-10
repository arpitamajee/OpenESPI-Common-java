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
import java.util.UUID;

import org.energyos.espi.common.domain.MeterReading;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.repositories.MeterReadingRepository;

/**
 * MeterReading Service
 * 
 * @author jat1
 *
 */
public interface MeterReadingService {

	public void setMeterReadingRepository(MeterReadingRepository meterReadingRepository);
	
	public MeterReading findById(Long retailCustomerId, Long usagePointId, Long meterReadingId);

	public MeterReading importResource(InputStream stream);
	
	/* LH customization starts here */
	MeterReading findByUUID(UUID uuid);
	
	public MeterReading findById(long meterReadingId);

	public MeterReading findByLink(String link);
		
	public void persist(MeterReading meterReading);
	
	public EntryType findEntryType(Long retailCustomerId, Long usagePointId,Long meterReadingId);

}
