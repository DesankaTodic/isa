package rs.ac.uns.ftn.informatika.jpa.service;

import rs.ac.uns.ftn.informatika.jpa.domain.users.SystemManager;

public interface SystemManagerService {

	public SystemManager addManager(SystemManager manag);
	
	public SystemManager getManager(Long id);
}
