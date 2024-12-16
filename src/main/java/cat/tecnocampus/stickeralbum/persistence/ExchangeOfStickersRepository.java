package cat.tecnocampus.stickeralbum.persistence;

import cat.tecnocampus.stickeralbum.application.outputDTOs.ExchangeOfStickersDTO;
import cat.tecnocampus.stickeralbum.domain.Collector;
import cat.tecnocampus.stickeralbum.domain.ExchangeOfStickers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExchangeOfStickersRepository extends JpaRepository<ExchangeOfStickers, Long> {

    @Query("""
        SELECT new cat.tecnocampus.stickeralbum.application.outputDTOs.ExchangeOfStickersDTO(
            e.id, e.origin.album.id, e.origin.album.name, e.origin.collector.id, e.origin.collector.name, 
            e.destination.collector.id, e.destination.collector.name, e.proposalDate)
        FROM ExchangeOfStickers e 
        WHERE e.origin.collector = :collector AND e.status = 'PENDING'""")
    List<ExchangeOfStickersDTO> findPendingByOriginUser(Collector collector);

    // TODO 5.2 Add a query to retrieve the pending exchanges for a destination user
    //  Uncomment the following query and complete it
    //  Once you have completed the query, you can uncomment the test in TODO5_Tests
    //List<ExchangeOfStickersDTO> findPendingByDestinationUser(Collector collector);

    // TODO 5.3 Add a query to retrieve the stickers the origin collector is giving in the exchange
    //  Uncomment the following query and complete it
    //  Once you have completed the query, you can uncomment the test in TODO5_Tests
    //List<SitckerInExchangeDTO> findExchangeGiveStickers(Long exchangeId);

    // TODO 5.4 Add a query to retrieve the stickers the destination collector is receiving in the exchange
    //  Uncomment the following query and complete it
    //  Once you have completed the query, you can uncomment the test in TODO5_Tests
    //List<SitckerInExchangeDTO> findExchangeReceiveStickers(Long exchangeId);
}
