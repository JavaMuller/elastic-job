/*
 * Copyright 1999-2015 dangdang.com.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package com.dangdang.ddframe.job.lite.spring.integrate;

import com.dangdang.ddframe.job.lite.fixture.FooSimpleElasticJob;
import com.dangdang.ddframe.job.lite.fixture.ThroughputDataflowElasticJob;
import com.dangdang.ddframe.job.lite.internal.schedule.JobRegistry;
import com.dangdang.ddframe.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.test.AbstractZookeeperJUnit4SpringContextTests;
import lombok.RequiredArgsConstructor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RequiredArgsConstructor
public abstract class AbstractJobSpringIntegrateTest extends AbstractZookeeperJUnit4SpringContextTests {
    
    private final String simpleJobName;
    
    private final String throughputDataflowJobName;
    
    @Resource
    private CoordinatorRegistryCenter regCenter;
    
    @Before
    @After
    public void reset() {
        FooSimpleElasticJob.reset();
        ThroughputDataflowElasticJob.reset();
    }
    
    @After
    public void tearDown() {
        JobRegistry.getInstance().getJobScheduleController(simpleJobName).shutdown();
        JobRegistry.getInstance().getJobScheduleController(throughputDataflowJobName).shutdown();
    }
    
    @Test
    public void assertSpringJobBean() {
        assertSimpleElasticJobBean();
        assertThroughputDataflowElasticJobBean();
    }
    
    private void assertSimpleElasticJobBean() {
        while (!FooSimpleElasticJob.isCompleted() || null == FooSimpleElasticJob.getJobValue()) {
            sleep(100L);
        }
        assertTrue(FooSimpleElasticJob.isCompleted());
        assertThat(FooSimpleElasticJob.getJobValue(), is("simple"));
        assertTrue(regCenter.isExisted("/" + simpleJobName + "/execution"));
    }
    
    private void assertThroughputDataflowElasticJobBean() {
        while (!ThroughputDataflowElasticJob.isCompleted()) {
            sleep(100L);
        }
        assertTrue(ThroughputDataflowElasticJob.isCompleted());
        assertTrue(regCenter.isExisted("/" + throughputDataflowJobName + "/execution"));
    }
    
    private static void sleep(final long millis) {
        try {
            Thread.sleep(millis);
        } catch (final InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
