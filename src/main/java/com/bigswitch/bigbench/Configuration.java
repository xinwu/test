package com.bigswitch.bigbench;

import java.util.HashMap;
import java.util.Vector;

import org.projectfloodlight.packet.Ethernet;
import org.projectfloodlight.packet.IPv4;

public class Configuration {

	private String _comment;
	public HashMap<String, Switch> switches;
	public HashMap<String, Edge> edges;

	public Configuration() {
		setComment("no comment");
		switches = new HashMap<String, Switch>();
        edges = new HashMap<String, Edge>();
	}
	
	public String getComment() { 
		return _comment; 
	}
	
	public void setComment(String comment) {
		_comment = comment; 
	}
	
	/**
	 * switch
	 * */
	public static class Switch {
        private String name;
        private long dpid;
        private String type;
        private String portToSpine;
        private String portToLeaf;
        private String portToIvs;
        private Vector<SwitchPort> switchPorts;


        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public long getDpid() {
            return dpid;
        }
        public void setDpid(long dpid) {
            this.dpid = dpid;
        }
        public String getType() {
            return type;
        }
        public void setType(String type) {
            this.type = type;
        }
        public String getPortToSpine() {
            return portToSpine;
        }
        public void setPortToSpine(String portToSpine) {
            this.portToSpine = portToSpine;
        }
        public String getPortToLeaf() {
            return portToLeaf;
        }
        public void setPortToLeaf(String portToLeaf) {
            this.portToLeaf = portToLeaf;
        }
        public String getPortToIvs() {
            return portToIvs;
        }
        public void setPortToIvs(String portToIvs) {
            this.portToIvs = portToIvs;
        }

        public static class SwitchPort {
            private String portName;
            private String intfIpAddr;
            private String intfMacAddr;
            private long intfMacAddrLong;
            private int intfIpAddrInt;

            public String getPortName() {
                return portName;
            }
            public void setPortName(String portName) {
                this.portName = portName;
            }
            public String getIpAddr() {
                return intfIpAddr;
            }
            public void setIpAddr(String intfIpAddr) {
                this.intfIpAddr = intfIpAddr;
                this.intfIpAddrInt = IPv4.toIPv4Address(intfIpAddr);
            }
            public String getMacAddr() {
                return intfMacAddr;
            }
            public void setMacAddr(String intfMacAddr) {
                this.intfMacAddr = intfMacAddr;
                this.setIntfMacAddrLong(Ethernet.toLong(
                                           Ethernet.toMACAddress(intfMacAddr)));
            }
            public long getIntfMacAddrLong() {
                return intfMacAddrLong;
            }
            public void setIntfMacAddrLong(long intfMacAddrLong) {
                this.intfMacAddrLong = intfMacAddrLong;
            }
            public int getIpAddrInt() {
                return intfIpAddrInt;
            }
        }

        public void setSwitchPorts(Vector<SwitchPort> switchPorts) {
            this.switchPorts = switchPorts;
        }

        public Vector<SwitchPort> getsetSwitchPorts() {
            return this.switchPorts;
        }

    }
    public void setSwitches(HashMap<String, Switch> s) {
        switches = s;
    }
    public void addSwitch(Switch s) {
        switches.put(s.getName(), s);
    }
    public HashMap<String, Switch> getSwitches() {
        return switches;
    }
    
    public static class Edge {
        String[] link;
        String[] port;
        private boolean ignoreExternalLink;

        public netEdge() {
            link = new String[2];
            // In junit style testing, 'port' is portName. For router links
            // in json description 'port' is portNumber.
            port = new String[2];
        }
        public netEdge(String[] link) {
            this.link = new String[2];
            this.port = new String[2];
            setLink(link);
        }

        public netEdge(String[] link, String[] port) {
            this.link = new String[2];
            this.port = new String[2];
            setLink(link, port);
        }
        public void setLink(String[] l) {
            String[] temp0 = l[0].split(":");
            String[] temp1 = l[1].split(":");
            link[0] = temp0[0];
            link[1] = temp1[0];
            if (temp0.length > 1)
                port[0] = temp0[1];
            if (temp1.length > 1)
                port[1] = temp1[1];
        }

        public void setLink(String[] link, String[] port) {
            setLink(link);
            this.port[0] = port[0];
            this.port[1] = port[1];
        }

        @JsonProperty("ignoreExternalLink")
        public boolean isIgnoreExternalLink() {
            return ignoreExternalLink;
        }
        @JsonProperty("ignoreExternalLink")
        public void setNofRouter(boolean ignoreExternalLink) {
            this.ignoreExternalLink = ignoreExternalLink;
        }

    }
    public void setEdges(List<netEdge> e) {
        netEdges = e;
    }

    public void addEdge(netEdge e) {
        netEdges.add(e);
    }
}
