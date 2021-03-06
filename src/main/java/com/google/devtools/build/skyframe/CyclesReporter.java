// Copyright 2014 The Bazel Authors. All rights reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.google.devtools.build.skyframe;

import com.google.common.collect.ImmutableList;
import com.google.devtools.build.lib.events.EventHandler;
import com.google.devtools.build.lib.util.Preconditions;

/**
 * An utility for custom reporting of errors from cycles in the the Skyframe graph. This class is
 * stateful in order to differentiate between new cycles and cycles that have already been
 * reported (do not reuse the instances or cache the results as it could end up printing
 * inconsistent information or leak memory). It treats two cycles as the same if they contain the
 * same {@link SkyKey}s in the same order, but perhaps with different starting points. See
 * {@link CycleDeduper} for more information.
 */
public class CyclesReporter {

  /**
   * Interface for reporting custom information about a single cycle.
   */
  public interface SingleCycleReporter {

    /**
     * Reports the given cycle and returns {@code true}, or return {@code false} if this
     * {@link SingleCycleReporter} doesn't know how to report the cycle.
     *
     * @param topLevelKey the top level key that transitively depended on the cycle
     * @param cycleInfo the cycle
     * @param alreadyReported whether the cycle has already been reported to the
     *        {@link CyclesReporter}.
     * @param eventHandler the eventHandler to which to report the error
     */
    boolean maybeReportCycle(SkyKey topLevelKey, CycleInfo cycleInfo, boolean alreadyReported,
        EventHandler eventHandler);
  }

  private final ImmutableList<SingleCycleReporter> cycleReporters;
  private final CycleDeduper<SkyKey> cycleDeduper = new CycleDeduper<>();

  /**
   * Constructs a {@link CyclesReporter} that delegates to the given {@link SingleCycleReporter}s,
   * in the given order, to report custom information about cycles.
   */
  public CyclesReporter(SingleCycleReporter... cycleReporters) {
    this.cycleReporters = ImmutableList.copyOf(cycleReporters);
  }

  /**
   * Reports the given cycles, differentiating between cycles that have already been reported.
   *
   * @param cycles The {@code Iterable} of cycles.
   * @param topLevelKey This key represents the top level value key that returned cycle errors.
   * @param eventHandler the eventHandler to which to report the error
   */
  public void reportCycles(Iterable<CycleInfo> cycles, SkyKey topLevelKey,
      EventHandler eventHandler) {
    Preconditions.checkNotNull(eventHandler);
    for (CycleInfo cycleInfo : cycles) {
      boolean alreadyReported = false;
      if (!cycleDeduper.seen(cycleInfo.getCycle())) {
        alreadyReported = true;
      }
      boolean successfullyReported = false;
      for (SingleCycleReporter cycleReporter : cycleReporters) {
        if (cycleReporter.maybeReportCycle(topLevelKey, cycleInfo, alreadyReported, eventHandler)) {
          successfullyReported = true;
          break;
        }
      }
      Preconditions.checkState(successfullyReported,
          printArbitraryCycle(topLevelKey, cycleInfo, alreadyReported));
    }
  }

  private String printArbitraryCycle(SkyKey topLevelKey, CycleInfo cycleInfo,
      boolean alreadyReported) {
    StringBuilder cycleMessage = new StringBuilder()
        .append("topLevelKey: " + topLevelKey + "\n")
        .append("alreadyReported: " + alreadyReported + "\n")
        .append("path to cycle:\n");
    for (SkyKey skyKey : cycleInfo.getPathToCycle()) {
      cycleMessage.append(skyKey + "\n");
    }
    cycleMessage.append("cycle:\n");
    for (SkyKey skyKey : cycleInfo.getCycle()) {
      cycleMessage.append(skyKey + "\n");
    }
    return cycleMessage.toString();
  }
}
