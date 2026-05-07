package com.tradeflow.dto;

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
