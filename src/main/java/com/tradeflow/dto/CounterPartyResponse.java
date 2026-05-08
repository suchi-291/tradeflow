package com.tradeflow.dto;

//After trade is saved, we want to return clean output.We do not want to return raw entity because entity may contain lazy-loaded objects, unwanted fields, or internal database structure. Buyer and seller are both CounterParty. So we create one reusable response class. This class is used inside TradeResponse like this : private CounterPartyResponse buyer; private CounterPartyResponse seller;

public class CounterPartyResponse {
		private Long id;
		private String lei;
		private String name;
		
		public CounterPartyResponse() {
			
		}

		public CounterPartyResponse(Long id, String lei, String name) {
			super();
			this.id = id;
			this.lei = lei;
			this.name = name;
		}

		public Long getId() {
			return id;
		}

		public String getLei() {
			return lei;
		}

		public String getName() {
			return name;
		}

		
		
}
