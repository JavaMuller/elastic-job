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

package com.dangdang.ddframe.job.api.internal.executor;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.config.JobRootConfiguration;
import com.dangdang.ddframe.job.api.exception.JobExecutionEnvironmentException;

import java.util.Collection;

/**
 * 作业内部服务门面服务.
 * 
 * @author zhangliang
 */
public interface JobFacade {
    
    /**
     * 读取作业配置.
     * 
     * @param fromCache 是否从缓存中读取
     * @return 作业配置
     */
    JobRootConfiguration loadJobRootConfiguration(boolean fromCache);
    
    /**
     * 检查作业执行环境.
     * 
     * @throws JobExecutionEnvironmentException 作业执行环境异常
     */
    void checkJobExecutionEnvironment() throws JobExecutionEnvironmentException;
    
    /**
     * 如果需要失效转移, 则设置作业失效转移.
     */
    void failoverIfNecessary();
    
    /**
     * 注册作业启动信息.
     *
     * @param shardingContext 分片上下文
     */
    void registerJobBegin(ShardingContext shardingContext);
    
    /**
     * 注册作业完成信息.
     *
     * @param shardingContext 分片上下文
     */
    void registerJobCompleted(ShardingContext shardingContext);
    
    /**
     * 获取当前作业服务器的分片上下文.
     *
     * @return 分片上下文
     */
    ShardingContext getShardingContext();
    
    /**
     * 设置任务被错过执行的标记.
     *
     * @param shardingItems 需要设置错过执行的任务分片项
     * @return 是否满足misfire条件
     */
    boolean misfireIfNecessary(Collection<Integer> shardingItems);
    
    /**
     * 清除任务被错过执行的标记.
     *
     * @param shardingItems 需要清除错过执行的任务分片项
     */
    void clearMisfire(Collection<Integer> shardingItems);
    
    /**
     * 判断作业是否需要执行错过的任务.
     * 
     * @param shardingItems 任务分片项集合
     * @return 作业是否需要执行错过的任务
     */
    boolean isExecuteMisfired(Collection<Integer> shardingItems);
    
    /**
     * 判断作业是否符合继续运行的条件.
     * 
     * <p>如果作业停止或需要重分片或非流式处理则作业将不会继续运行.</p>
     * 
     * @return 作业是否符合继续运行的条件
     */
    boolean isEligibleForJobRunning();
    
    /**判断是否需要重分片.
     *
     * @return 是否需要重分片
     */
    boolean isNeedSharding();
    
    /**
     * 清理作业上次运行时信息.
     * 只会在主节点进行.
     */
    void cleanPreviousExecutionInfo();
    
    /**
     * 作业执行前的执行的方法.
     *
     * @param shardingContext 分片上下文
     */
    void beforeJobExecuted(ShardingContext shardingContext);
    
    /**
     * 作业执行后的执行的方法.
     *
     * @param shardingContext 分片上下文
     */
    void afterJobExecuted(ShardingContext shardingContext);
}
