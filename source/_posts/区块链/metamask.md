---
title: MetaMask
date: 2021-11-12 15:24:19
tags: [小狐狸,MetaMask,区块链,钱包]
categories: [区块链,钱包]
---

## 小狐狸钱包基本操作方法

```js
import MetaMaskOnboarding from '@metamask/onboarding'
import ElLoading from 'element-plus/es/components/loading/index'
import 'element-plus/es/components/loading/style/index'
import BigNumber from 'bignumber.js'
import { ethers } from 'ethers'

const { ethereum } = window
const web3 = new Web3('https://goerli.infura.io/v3/9aa3d95b3bc440fa88ea12eaa4456161')
// const web3 = new Web3(import.meta.env.VITE_app_POLYGON_URL)
let ethersProvider
let maticPOSClientParent
let maticPOSClientMatic

export const personalSign = async function (address, randomStr) {
    try {
        const msg = `0x${window.Buffer.from(randomStr, 'utf8').toString('hex')}`
        const sign = await ethereum.request({
            method: 'personal_sign',
            params: [msg, address, 'Example password'],
        })
        return sign
        // personalSignResult.innerHTML = sign
        // personalSignVerify.disabled = false
    } catch (err) {
        console.error(err)
        // personalSign.innerHTML = `Error: ${err.message}`
    }
}
/**
 * 判断当前浏览器是否安装 metamask插件
 */
export const isMetaMaskInstalled = function () {
    let eth = Boolean(ethereum && ethereum.isMetaMask)
    if (eth) {
        ethersProvider = new ethers.providers.Web3Provider(ethereum, 'any')
        maticPOSClientParent = new Matic.MaticPOSClient({
            network: 'testnet',
            version: 'mumbai',
            parentProvider: new Web3(ethereum).currentProvider,
            maticProvider: 'https://rpc-mumbai.maticvigil.com/v1/423e0658d71aae08dd5d0eb56b3d379399c40e1b',
        })
        maticPOSClientMatic = new Matic.MaticPOSClient({
            network: 'testnet',
            version: 'mumbai',
            parentProvider: 'https://goerli.infura.io/v3/5cf8a212d9e34aca9cbcc58800551ee3',
            maticProvider: new Web3(ethereum).currentProvider,
        })
    }
    return eth
}
/**
 * 检查当前网站是否连接钱包，如果没有安装钱包则安装，如果安装则连接
 * @param forwarderOrigin 当前网页url
 */
export const MetaMaskClientCheck = async function (forwarderOrigin) {
    if (!isMetaMaskInstalled()) {
        onInstall(forwarderOrigin)
    } else {
        return onConnect()
    }
}

/**
 * 安装metamask浏览器扩展钱包
 * @param forwarderOrigin 当前网址
 */
export const onInstall = forwarderOrigin => {
    const onboarding = new MetaMaskOnboarding({ forwarderOrigin })
    onboarding.startOnboarding()
}

/**
 * 连接小狐狸钱包
 */
export const onConnect = async function () {
    if (ethereum) {
        let result = await ethereum.request({ method: 'eth_requestAccounts' }).catch(err => {
            alert('Please connect your metaMask')
        })
        return result
    } else {
        alert('Wallet not downloaded')
    }
}

/**
 * 获取当前账户
 */
export const getAccount = async function () {
    try {
        return await ethereum.request({
            method: 'eth_requestAccounts',
        })
    } catch (error) {
        console.error(error)
    }
}
/**
 * 查询账户eth余额
 * @param address 钱包地址
 * @returns {Promise<BigNumber>}
 */
export const getBalance = async function (address) {
    var loading = ElLoading.service({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)',
    })
    try {
        let res = await web3.eth.getBalance(address, 'latest')
        loading.close()
        return res
    } catch (error) {
        // console.error(error);
        loading.close()
        // console.log(error)
        onConnect()
    }
}

/**
 * 发送ETH
 * @param receiveAddress 接收人地址
 * @param value 转账数量
 * @param gasLimit gas费限制
 * @param gasPrice gas费价格
 */
export const sendEth = async function (receiveAddress, value, gasLimit = 21000, gasPrice = 20000000000) {
    if (isMetaMaskInstalled) {
        return await ethersProvider.getSigner().sendTransaction({
            to: receiveAddress,
            value: value,
            gasLimit: gasLimit,
            gasPrice: gasPrice,
        })
    } else {
        return false
    }
}
/**
 * 发送合约
 * @param receiveAddress 接收人地址
 * @param data 物品tokenId[]
 * @param value 价格
 * @param gasLimit gas费限制
 * @param gasPrice gas费价格
 * @returns {Promise<TransactionResponse>}
 */
export const sendContact = async function (receiveAddress, data, value = 0, gasLimit = 500000, gasPrice = 30000000000) {
    if (isMetaMaskInstalled) {
        // try {
        console.log(receiveAddress, data, value, gasLimit, gasPrice)
        return await ethersProvider.getSigner().sendTransaction({
            to: receiveAddress,
            value: value,
            data: data,
            gasLimit: gasLimit,
            gasPrice: gasPrice,
            // maxFeePerGas: gasPrice,
            // maxPriorityFeePerGas: '0x59682F00',
            // gas:1.1
        })
        // } catch (error) {
        //     // console.error(error);
        //     alert(error.code)
        //     console.log(this)
        //     return error
        // }
    } else {
        return false
    }
}
export const gas = async function (from, to, data) {
    return await web3.eth.estimateGas({
        from,
        to,
        data,
    })
}
export const gasPrice = async function () {
    return await web3.eth.getGasPrice()
}
/**
 * 添加代币
 * @param type
 * @param contractAddress
 * @param tokenSymbol
 * @param decimalUnits
 * @param image
 * @returns {Promise<*>}
 */
export const addTokenToWallet = async (
    type = 'ERC20',
    contractAddress,
    tokenSymbol,
    decimalUnits = 0,
    image = 'https://metamask.github.io/test-dapp/metamask-fox.svg',
) => {
    return await ethereum.request({
        method: 'wallet_watchAsset',
        params: {
            type: type,
            options: {
                address: contractAddress,
                symbol: tokenSymbol,
                decimals: decimalUnits,
                image: image,
            },
        },
    })
}
/**
 * 创建合约方法对象
 * @param contractAbi
 * @param contractAddress
 * @param currentAccount
 * @returns {Promise<*>}
 */
export const contactMethod = function (contractAbi, contractAddress, currentAccount) {
    let myContract = new web3.eth.Contract(contractAbi, contractAddress, {
        from: currentAccount,
    })
    return myContract.methods
}
/**
 * 添加网络
 * @returns {Promise<void>}
 */
export const addChain = async (
    chainId = '0x89',
    rpcUrls = ['https://polygon-rpc.com/'],
    chainName = 'Polygon Mainnet',
    nativeCurrency = {
        name: 'MATIC',
        decimals: 18,
        symbol: 'MATIC',
    },
    blockExplorerUrls = ['https://polygonscan.com/'],
) => {
    await ethereum.request({
        method: 'wallet_addEthereumChain',
        params: [
            {
                chainId: chainId,
                rpcUrls: rpcUrls,
                chainName: chainName,
                nativeCurrency: nativeCurrency,
                blockExplorerUrls: blockExplorerUrls,
            },
        ],
    })
}
/**
 * 切换网络
 * @returns {Promise<void>}
 */
export const switchChain = async chainId => {
    console.log(ethereum)
    try {
        await ethereum.request({
            method: 'wallet_switchEthereumChain',
            params: [
                {
                    chainId: chainId,
                },
            ],
        })
    } catch (switchError) {
        // This error code indicates that the chain has not been added to MetaMask.
        if (switchError.code === 4902) {
            try {
                addChain()
            } catch (addError) {
                // handle "add" error
            }
        }
        // handle other "switch" errors
    }
}

export const buyArray = async (contractAbi, contactAddress, address, value, ids) => {
    let abiMethods = contactMethod(contractAbi, contactAddress, address)
    abiMethods.then(fun => {
        let data = fun.safeBatchMintCNG(ids).encodeABI()

        sendContact(contactAddress, data, value).then(
            sendContactResult => {
                var result = 'approve'
                var txHash = sendContactResult.hash
                walletResult({
                    result,
                    appId: appId,
                    address: address,
                    txHash: txHash,
                    cost: 'wei',
                })
            },
            err => {
                var result = 'reject'
                let txHash
                walletResult({
                    result,
                    appId: appId,
                    address: address,
                    txHash: txHash,
                    cost: 'wei',
                })
                console.log('交易完成')
            },
        )
    })
}

export const changeWei = function (value) {
    return web3.utils.toWei(value.toString(), 'ether')
}
export const changeFromWei = function (value) {
    return web3.utils.fromWei(value.toString(), 'Gwei')
}

export const deposit20 = async (rootToken, amount, from) => {
    await switchChain('0x5')
    const approveResult = await maticPOSClientParent.approveERC20ForDeposit(rootToken, amount, { from: from })
    console.log(approveResult)
    const depositResult = await maticPOSClientParent.depositERC20ForUser(rootToken, from, amount, { from })
    console.log(depositResult)
    return depositResult
}

export const burn20 = async function (childToken, amount, from) {
    await switchChain('0x13881')
    console.log(maticPOSClientMatic)
    const burnTxHash = await maticPOSClientMatic.burnERC20(childToken, amount, { from }).catch(e => {
        console.log(e)
    })
    console.log(burnTxHash)
}

export const exit20 = async (burnTxHash, from) => {
    await switchChain('0x5')
    const exitResult = await maticPOSClientParent.exitERC20(burnTxHash, { from })
    console.log(exitResult)
}

export const deposit721 = async (rootToken, tokenId, from) => {
    await switchChain('0x5')
    const approveResult = await maticPOSClientParent.approveERC721ForDeposit(rootToken, tokenId, { from })
    console.log(approveResult)
    // let gasPrice = await gasPrice();
    const depositResult = await maticPOSClientParent.depositERC721ForUser(rootToken, from, tokenId, { from })
    console.log(depositResult)
    return depositResult
}

export const burn721 = async (childToken, tokenId, from) => {
    await switchChain('0x13881')
    console.log(childToken, tokenId, from, maticPOSClientMatic)
    const burnTxHash = await maticPOSClientMatic.burnERC721(childToken, tokenId, { from })
    return burnTxHash
}

export const exit721 = async (burnTxHash, from) => {
    await switchChain('0x5')
    const exitResult = await maticPOSClientParent.exitERC721(burnTxHash, { from })
    return exitResult
}

```