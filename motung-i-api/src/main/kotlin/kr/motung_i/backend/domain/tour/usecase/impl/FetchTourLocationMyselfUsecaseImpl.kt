package kr.motung_i.backend.domain.tour.usecase.impl

import kr.motung_i.backend.domain.tour.presentation.dto.response.FetchTourLocationMyselfResponse
import kr.motung_i.backend.domain.tour.presentation.dto.response.GeometryResponse
import kr.motung_i.backend.domain.tour.usecase.FetchTourLocationMyselfUsecase
import kr.motung_i.backend.domain.user.usecase.FetchCurrentUserUsecase
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.global.geojson.LocalsCache
import kr.motung_i.backend.persistence.tour.repository.TourRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class FetchTourLocationMyselfUsecaseImpl(
    private val tourRepository: TourRepository,
    private val fetchCurrentUserUsecase: FetchCurrentUserUsecase,
    private val localsCache: LocalsCache,
): FetchTourLocationMyselfUsecase {
    override fun execute(): FetchTourLocationMyselfResponse {
        val currentUser = fetchCurrentUserUsecase.execute()
        val tour = tourRepository.findByUserAndIsActive(currentUser, true)
            ?: throw CustomException(CustomErrorCode.NOT_ACTIVATED_TOUR)

        val geometry = localsCache.findGeometryByLocal(tour.local)
            ?: throw CustomException(CustomErrorCode.NOT_FOUND_LOCAL)

        return FetchTourLocationMyselfResponse(
            lat = tour.location.lat,
            lon = tour.location.lon,
            local = tour.local.localAlias,
            geometry = GeometryResponse.fromMultiPolygon(geometry)
        )
    }
}