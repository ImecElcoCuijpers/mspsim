/**
 * Copyright (c) 2012, Swedish Institute of Computer Science.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. Neither the name of the Institute nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE INSTITUTE AND CONTRIBUTORS ``AS IS'' AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE INSTITUTE OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
 * OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 *
 * This file is part of MSPSim.
 */
package se.sics.mspsim.chip;
import se.sics.mspsim.core.Chip;
import se.sics.mspsim.core.MSP430Core;

/**
 * @author Niclas Finne
 */
public abstract class Radio802154 extends Chip implements RFListener, RFSource {

    // The Operation modes of the 802.15.4 radios
    public static final int MODE_TXRX_OFF = 0x00;
    public static final int MODE_RX_ON = 0x01;
    public static final int MODE_TXRX_ON = 0x02;
    public static final int MODE_POWER_OFF = 0x03;
    public static final int MODE_MAX = MODE_POWER_OFF;
    private static final String[] MODE_NAMES = new String[] {
      "off", "listen", "transmit", "power_off"
    };

    protected RFListener rfListener;
    protected ChannelListener channelListener;

    public Radio802154(String id, String name, MSP430Core cpu) {
        super(id, name, cpu);
        setModeNames(MODE_NAMES);
        setMode(MODE_POWER_OFF);
    }

    public abstract boolean isReadyToReceive();

    @Override
    public abstract void receivedByte(byte c);

    public abstract int getActiveChannel();
    public abstract int getActiveFrequency();
    public abstract int getOutputPower();
    public abstract int getOutputPowerMax();
    public abstract int getOutputPowerIndicator();
    public abstract int getOutputPowerIndicatorMax();

    public abstract int getRSSI();
    public abstract void setRSSI(int rssi);

    public abstract int getLQI();
    public abstract void setLQI(int lqi);

    @Override
    public int getModeMax() {
        return MODE_MAX;
    }

    @Override
    public synchronized void addRFListener(RFListener rf) {
        rfListener = RFListener.Proxy.INSTANCE.add(rfListener, rf);
    }

    @Override
    public synchronized void removeRFListener(RFListener rf) {
        rfListener = RFListener.Proxy.INSTANCE.remove(rfListener, rf);
    }

    public synchronized void addChannelListener(ChannelListener listener) {
        channelListener = ChannelListener.Proxy.INSTANCE.add(channelListener, listener);
    }

    public synchronized void removeChannelListener(ChannelListener listener) {
        channelListener = ChannelListener.Proxy.INSTANCE.remove(channelListener, listener);
    }

}
