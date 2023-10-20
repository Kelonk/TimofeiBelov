package HW.concurrency.models.enrich;

import HW.concurrency.enums.EnrichType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnrichMSISDNTest {

  @Test
  void enrichType() {
    EnrichAction enrichMSISDN = new EnrichMSISDN();
    EnrichTestDefault.enrichType(enrichMSISDN, EnrichType.MSISDN);
  }

  @Test
  void enrich() {
    EnrichAction enrichMSISDN = new EnrichMSISDN();
    EnrichTestDefault.enrich(enrichMSISDN);
  }
}