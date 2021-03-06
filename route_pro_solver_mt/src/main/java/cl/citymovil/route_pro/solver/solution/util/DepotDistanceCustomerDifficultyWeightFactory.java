/*
 * Copyright 2014 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cl.citymovil.route_pro.solver.solution.util;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.optaplanner.core.impl.heuristic.selector.common.decorator.SelectionSorterWeightFactory;

import cl.citymovil.route_pro.solver.domain.Customer;
import cl.citymovil.route_pro.solver.domain.Depot;
import cl.citymovil.route_pro.solver.domain.VehicleRoutingSolution;

/**
 * On large datasets, the constructed solution looks like a Matryoshka doll.
 */
public class DepotDistanceCustomerDifficultyWeightFactory
        implements SelectionSorterWeightFactory<VehicleRoutingSolution, Customer> {

    public Comparable createSorterWeight(VehicleRoutingSolution vehicleRoutingSolution, Customer customer) {
        Depot depot = vehicleRoutingSolution.getDepotList().get(0);
        return new DepotDistanceCustomerDifficultyWeight(customer,
                (int) (customer.getLocation().getMetersFrom(depot.getLocation())
                        + depot.getLocation().getMetersTo(customer.getLocation())));
    }

    public static class DepotDistanceCustomerDifficultyWeight
            implements Comparable<DepotDistanceCustomerDifficultyWeight> {

        private final Customer customer;
        private final int depotRoundTripDistance;

        public DepotDistanceCustomerDifficultyWeight(Customer customer,
                int depotRoundTripDistance) {
            this.customer = customer;
            this.depotRoundTripDistance = depotRoundTripDistance;
        }

        public int compareTo(DepotDistanceCustomerDifficultyWeight other) {
            return new CompareToBuilder()
                    .append(depotRoundTripDistance, other.depotRoundTripDistance) // Ascending (further from the depot are more difficult)
//                    .append(customer.getDemand(), other.customer.getDemand())
//                    .append(customer.getLocation().getLatitude(), other.customer.getLocation().getLatitude())
//                    .append(customer.getLocation().getLongitude(), other.customer.getLocation().getLongitude())
//                    .append(customer.getCustomerId(), other.customer.getCustomerId())
                    .toComparison();
        }

    }

}
