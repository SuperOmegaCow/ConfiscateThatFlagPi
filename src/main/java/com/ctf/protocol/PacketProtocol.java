package com.ctf.protocol;

import com.ctf.protocol.packets.*;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;

import java.lang.reflect.Constructor;

public class PacketProtocol {

    public static final int MAX_PACKET_ID = 5;

    public static final ProtocolData INBOUND = new ProtocolData();
    public static final ProtocolData OUTBOUND = new ProtocolData();

    static {
        INBOUND.registerPacket(0, Login.class);
        INBOUND.registerPacket(1, Accelerometer.class);
        INBOUND.registerPacket(2, Gyroscope.class);
        INBOUND.registerPacket(3, Line.class);
        INBOUND.registerPacket(4, Location.class);
        INBOUND.registerPacket(5, Status.class);

        OUTBOUND.registerPacket(0, Login.class);
        OUTBOUND.registerPacket(1, Accelerometer.class);
        OUTBOUND.registerPacket(2, Gyroscope.class);
        OUTBOUND.registerPacket(3, Line.class);
        OUTBOUND.registerPacket(4, Location.class);
        OUTBOUND.registerPacket(5, Status.class);
    }

    public static class ProtocolData {

        private final TObjectIntMap<Class<? extends DefinedPacket>> packetMap = new TObjectIntHashMap<Class<? extends DefinedPacket>>(MAX_PACKET_ID);
        private final Class<? extends DefinedPacket>[] packetClasses = new Class[MAX_PACKET_ID];
        private final Constructor<? extends DefinedPacket>[] packetConstructors = new Constructor[MAX_PACKET_ID];

        public boolean hasPacket(int id) {
            return id < MAX_PACKET_ID && packetConstructors[id] != null;
        }

        public final DefinedPacket createPacket(int id) {
            if (id > MAX_PACKET_ID) {
                throw new BadPacketException("Packet with id " + id + " outside of range ");
            }
            if (packetConstructors[id] == null) {
                throw new BadPacketException("No packet with id " + id);
            }
            try {
                return packetConstructors[id].newInstance();
            } catch (ReflectiveOperationException ex) {
                throw new BadPacketException("Could not construct packet with id " + id, ex);
            }
        }

        protected final void registerPacket(int id, Class<? extends DefinedPacket> packetClass) {
            try {
                packetConstructors[id] = packetClass.getDeclaredConstructor();
            } catch (NoSuchMethodException ex) {
                throw new BadPacketException("No NoArgsConstructor for packet class " + packetClass);
            }
            packetClasses[id] = packetClass;
            packetMap.put(packetClass, id);
        }

        protected final void unregisterPacket(int id) {
            packetMap.remove(packetClasses[id]);
            packetClasses[id] = null;
            packetConstructors[id] = null;
        }

        final int getId(Class<? extends DefinedPacket> packet) {
            return packetMap.get(packet);
        }
    }

}
