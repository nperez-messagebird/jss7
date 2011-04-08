/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc. and/or its affiliates, and individual
 * contributors as indicated by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a full listing
 * of individual contributors.
 * 
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU General Public License, v. 2.0.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU 
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License,
 * v. 2.0 along with this distribution; if not, write to the Free 
 * Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301, USA.
 */
package org.mobicents.protocols.ss7.isup.impl.stack.timers;

import java.util.Properties;

import org.mobicents.protocols.ss7.isup.ISUPTimeoutEvent;
import org.mobicents.protocols.ss7.isup.message.CircuitGroupBlockingAckMessage;
import org.mobicents.protocols.ss7.isup.message.CircuitGroupBlockingMessage;
import org.mobicents.protocols.ss7.isup.message.ISUPMessage;
import org.mobicents.protocols.ss7.isup.message.parameter.CircuitGroupSuperVisionMessageType;
import org.mobicents.protocols.ss7.isup.message.parameter.CircuitIdentificationCode;
import org.mobicents.protocols.ss7.isup.message.parameter.RangeAndStatus;

/**
 * @author baranowb
 *
 */
public class CGBTest extends DoubleTimers {
	//thanks to magic of super class, this is whole test :)
	/* (non-Javadoc)
	 * @see org.mobicents.protocols.ss7.isup.impl.stack.DoubleTimers#getSmallerT()
	 */
	
	protected long getSmallerT() {
		return ISUPTimeoutEvent.T18_DEFAULT+3000;
	}

	/* (non-Javadoc)
	 * @see org.mobicents.protocols.ss7.isup.impl.stack.DoubleTimers#getBiggerT()
	 */
	
	protected long getBiggerT() {
		return ISUPTimeoutEvent.T19_DEFAULT;
	}

	/* (non-Javadoc)
	 * @see org.mobicents.protocols.ss7.isup.impl.stack.DoubleTimers#getSmallerT_ID()
	 */
	
	protected int getSmallerT_ID() {
		return ISUPTimeoutEvent.T18;
	}

	/* (non-Javadoc)
	 * @see org.mobicents.protocols.ss7.isup.impl.stack.DoubleTimers#getBiggerT_ID()
	 */
	
	protected int getBiggerT_ID() {
		return ISUPTimeoutEvent.T19;
	}

	/* (non-Javadoc)
	 * @see org.mobicents.protocols.ss7.isup.impl.stack.DoubleTimers#getRequest()
	 */
	
	protected ISUPMessage getRequest() {
		CircuitGroupBlockingMessage cgb = super.provider.getMessageFactory().createCGB(1); 
		
		RangeAndStatus ras = super.provider.getParameterFactory().createRangeAndStatus();
		ras.setRange((byte) 7,true);
		ras.setAffected((byte) 1, true);
		ras.setAffected((byte) 0, true);
		cgb.setRangeAndStatus(ras);

		CircuitGroupSuperVisionMessageType cgsvmt = super.provider.getParameterFactory().createCircuitGroupSuperVisionMessageType();
		cgsvmt.setCircuitGroupSuperVisionMessageTypeIndicator(cgsvmt._CIRCUIT_GROUP_SMTIHFO);
		cgb.setSupervisionType(cgsvmt);
		
		return cgb;
	}

	/* (non-Javadoc)
	 * @see org.mobicents.protocols.ss7.isup.impl.stack.DoubleTimers#getAnswer()
	 */
	
	protected ISUPMessage getAnswer() {
		CircuitGroupBlockingAckMessage cgba = super.provider.getMessageFactory().createCGBA();
		CircuitIdentificationCode cic = super.provider.getParameterFactory().createCircuitIdentificationCode();
		cic.setCIC(1);
		cgba.setCircuitIdentificationCode(cic);
		RangeAndStatus ras = super.provider.getParameterFactory().createRangeAndStatus();
		ras.setRange((byte) 7,true);
		ras.setAffected((byte) 1, true);
		ras.setAffected((byte) 0, true);
		cgba.setRangeAndStatus(ras);

		CircuitGroupSuperVisionMessageType cgsvmt = super.provider.getParameterFactory().createCircuitGroupSuperVisionMessageType();
		cgsvmt.setCircuitGroupSuperVisionMessageTypeIndicator(cgsvmt._CIRCUIT_GROUP_SMTIHFO);
		cgba.setSupervisionType(cgsvmt);
		return cgba;
	}

	/* (non-Javadoc)
	 * @see org.mobicents.protocols.ss7.isup.impl.stack.EventTestHarness#getSpecificConfig()
	 */
	
	protected Properties getSpecificConfig() {
		//ensure proper values;
		Properties p = new Properties();
		p.put("t18", getSmallerT()+"");
		p.put("t19" ,  getBiggerT()+"");
		return p;
	}

}