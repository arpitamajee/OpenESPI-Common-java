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

package org.energyos.espi.common.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.energyos.espi.common.domain.ApplicationInformation;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.repositories.ApplicationInformationRepository;
import org.energyos.espi.common.service.ApplicationInformationService;
import org.energyos.espi.common.service.ImportService;
import org.energyos.espi.common.service.ResourceService;
import org.energyos.espi.common.utils.EntryTypeIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional (rollbackFor= {javax.xml.bind.JAXBException.class}, 
                noRollbackFor = {javax.persistence.NoResultException.class, org.springframework.dao.EmptyResultDataAccessException.class })

public class ApplicationInformationServiceImpl implements ApplicationInformationService {
	
    @Autowired
    private ApplicationInformationRepository applicationInformationRepository;

	@Autowired
    private ResourceService resourceService;
    
    @Autowired
    private ImportService importService;
            
    @Override
    public List<ApplicationInformation> findByKind(String kind) {
        return applicationInformationRepository.findByKind(kind);
    }

    @Override
    public ApplicationInformation findByClientId(String clientId) {
        return applicationInformationRepository.findByClientId(clientId);
    }

    @Override
    public ApplicationInformation findByDataCustodianClientId(String dataCustodianClientId) {
        return applicationInformationRepository.findByDataCustodianClientId(dataCustodianClientId);
    }

	@Override
	public ApplicationInformation importResource(InputStream stream) {

		ApplicationInformation applicationInformation = null;
		try {
			importService.importData(stream, null);
			applicationInformation = importService.getEntries().get(0).getContent().getApplicationInformation();
		} catch (Exception e) {

		}
		return applicationInformation;
	}

   public void setApplicationInformationRepository(ApplicationInformationRepository applicationInformationRepository) {
        this.applicationInformationRepository = applicationInformationRepository;
   }

   public ApplicationInformationRepository getApplicationInformationRepository () {
        return this.applicationInformationRepository;
   }
   public void setResourceService(ResourceService resourceService) {
        this.resourceService = resourceService;
   }

   public ResourceService getResourceService () {
       return this.resourceService;
  }
   
   public void setImportService(ImportService importService) {
        this.importService = importService;
   }

   public ImportService getImportService () {
        return this.importService;
   }	
   /* LH customization starts here */
   @Override
   public ApplicationInformation findById(Long id) {
       return applicationInformationRepository.findById(id);
   }
   @Override
   public List<ApplicationInformation> findAll() {
       return applicationInformationRepository.findAll();
   }
   
   @Override
   public List<ApplicationInformation> findAllThirdParties() {
       return applicationInformationRepository.findAllThirdParties();
   }   
   @Override
	public void merge(ApplicationInformation applicationInformation) {
		// TODO Auto-generated method stub
	}
   @Override
   public void persist(ApplicationInformation applicationInformation) {
   	applicationInformationRepository.persist(applicationInformation);
   }   

}