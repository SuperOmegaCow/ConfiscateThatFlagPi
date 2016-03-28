package com.ctf.status;

import com.ctf.protocol.BadPacketException;
import com.ctf.util.StatusUpdates;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;

import java.lang.reflect.Constructor;

public class StatusProtocol {

    public static final int MAX_STATUS_ID = 101;

    public static final StatusData STATUS = new StatusData();

    static {
        STATUS.registerStatus(StatusUpdates.DISCONNECT_NO_REASON, DisconnectNoReason.class);
    }

    public static class StatusData {

        private final TObjectIntMap<Class<? extends StatusHandler>> statusMap = new TObjectIntHashMap<Class<? extends StatusHandler>>(MAX_STATUS_ID);
        private final Class<? extends StatusHandler>[] statusClasses = new Class[MAX_STATUS_ID];
        private final Constructor<? extends StatusHandler>[] statusConstructors = new Constructor[MAX_STATUS_ID];

        public boolean hasStatus(int id) {
            return id < MAX_STATUS_ID && statusConstructors[id] != null;
        }

        public final StatusHandler createStatus(int id) {
            if (id > MAX_STATUS_ID) {
                throw new BadPacketException("Status with id " + id + " outside of range ");
            }
            if (statusConstructors[id] == null) {
                throw new BadPacketException("No status with id " + id);
            }
            try {
                return statusConstructors[id].newInstance();
            } catch (ReflectiveOperationException ex) {
                throw new BadPacketException("Could not construct stats with id " + id, ex);
            }
        }

        protected final void registerStatus(int id, Class<? extends StatusHandler> statusClass) {
            try {
                statusConstructors[id] = statusClass.getDeclaredConstructor();
            } catch (NoSuchMethodException ex) {
                throw new BadStatusException("No NoArgsConstructor for status class " + statusClass);
            }
            statusClasses[id] = statusClass;
            statusMap.put(statusClass, id);
        }

        protected final void registerStatus(StatusUpdates statusUpdates, Class<? extends StatusHandler> statusClass) {
            try {
                statusConstructors[statusUpdates.getStatus()] = statusClass.getDeclaredConstructor();
            } catch (NoSuchMethodException ex) {
                throw new BadStatusException("No NoArgsConstructor for status class " + statusClass);
            }
            statusClasses[statusUpdates.getStatus()] = statusClass;
            statusMap.put(statusClass, statusUpdates.getStatus());
        }

        protected final void unregisterStatus(int id) {
            statusMap.remove(statusClasses[id]);
            statusClasses[id] = null;
            statusConstructors[id] = null;
        }

        final int getId(Class<? extends StatusHandler> status) {
            return statusMap.get(status);
        }

    }

}
