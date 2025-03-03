import useDocusaurusContext from '@docusaurus/useDocusaurusContext';
import Layout from '@theme/Layout';
import HomepageFeatures from '@site/src/components/HomepageFeatures';
import {useEffect, useState} from "react";

function ApplicationDetailsDashboard() {

    const {siteConfig} = useDocusaurusContext();

    const [details, setDetails] = useState(
        {
            environment: undefined,
            port: undefined,
            contextPath: undefined,
            group: undefined,
            artifactId: undefined,
            version: undefined,
            description: undefined,
            dateBuilt: undefined,
            timeSinceBuildText: undefined,
            dateStarted: undefined,
            timeSinceStartText: undefined,
            activeProfiles: undefined,
            springBootVersion: undefined,
            springVersion: undefined,
            javaVersion: undefined,
            metrics: [
                {
                    name: undefined,
                    value: undefined
                }
            ]
        }
    );

    const getApplicationDetails = async () => {
        return await fetch(siteConfig.customFields.applicationDetailsPath)
            .then(response => response.json());
    }

    useEffect(() => {
        getApplicationDetails()
            .then(data => setDetails(data));
    }, []);

    return (
        <>
            <header className="padding-bottom--md" style={{backgroundColor: "darkcyan"}}>
                <div className="container margin-vert--lg">
                    <div className="row">
                        <div className="col col--6">
                            <div className="card" style={{backgroundColor: ""}}>
                                <div className="card__header" style={{fontSize: '0.75em'}}>
                                    ENVIRONMENT / HOST
                                </div>
                                <div id="app.environment" className="card__body" style={{fontSize: '2em'}}>
                                    {details.environment}
                                </div>
                                <div className="card__body" style={{fontSize: '0.75em'}}>
                                    Port: {details.port}&nbsp;&nbsp;|&nbsp;&nbsp;Context Path: {details.contextPath}
                                </div>
                            </div>
                        </div>
                        <div className="col col--6">
                            <div className="card">
                                <div className="card__header" style={{fontSize: '0.75em'}}>
                                    VERSION
                                </div>
                                <div className="card__body" style={{fontSize: '2em'}}>
                                    {details.version}
                                </div>
                                <div className="card__body" style={{fontSize: '0.75em'}}>
                                    Group: {details.group}&nbsp;&nbsp;|&nbsp;&nbsp;ArtifactId: {details.artifactId}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="container margin-vert--md">
                    <div className="row">
                        <div className="col col--4">
                            <div className="card" style={{backgroundColor: ""}}>
                                <div className="card__header" style={{fontSize: '0.75em'}}>
                                    TIME SINCE BUILD
                                </div>
                                <div className="card__body" style={{fontSize: '1.5em'}}>
                                    {details.timeSinceBuildText}
                                </div>
                                <div className="card__body" style={{fontSize: '0.75em'}}>
                                    {details.dateBuilt}
                                </div>
                            </div>
                        </div>
                        <div className="col col--4">
                            <div className="card" style={{backgroundColor: ""}}>
                                <div className="card__header" style={{fontSize: '0.75em'}}>
                                    TIME SINCE START
                                </div>
                                <div className="card__body" style={{fontSize: '1.5em'}}>
                                    {details.timeSinceStartText}
                                </div>
                                <div className="card__body" style={{fontSize: '0.75em'}}>
                                    {details.dateStarted}
                                </div>
                            </div>
                        </div>
                        <div className="col col--4">
                            <div className="card" style={{backgroundColor: ""}}>
                                <div className="card__header" style={{fontSize: '0.75em'}}>
                                    ACTIVE PROFILES
                                </div>
                                <div className="card__body" style={{fontSize: '1.5em'}}>
                                    {details.activeProfiles}
                                </div>
                                <div className="card__body" style={{fontSize: '0.75em'}}>
                                    Spring Boot {details.springBootVersion} /
                                    Spring {details.springVersion} /
                                    Java {details.javaVersion}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </header>
            <main>
                <div className="container margin-vert--md">
                    <div className="row">
                        <div className="col col--4">
                            <div className="card">
                                <div className="card__header text--bold">
                                    Description
                                </div>
                                <div className="card__body">
                                    {details.description}
                                </div>
                            </div>
                        </div>
                        <div className="col col--4">
                            <div className="card">
                                <div className="card__header text--bold">
                                    Metrics
                                </div>
                                <div className="card__body">
                                    <table style={{border: 'none', display: 'table', width: '100%'}}>
                                        <tbody>
                                        {details.metrics.map(m=>
                                            <tr key={m.name}>
                                                <td style={{border: 'none'}}>{m.name}</td>
                                                <td style={{border: 'none'}}>{m.value}</td>
                                            </tr>
                                        )}
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                        <div className="col col--4">
                            <div className="card">
                                <div className="card__header text--bold">
                                    Links & Resources
                                </div>
                                <div className="card__body">
                                    <ul style={{listStyle: 'none', padding: 0}}>
                                        <li>
                                            <a href="postman/Spring + Docusaurus – JAX-RS.postman_collection.json"
                                               target="_blank">
                                                Postman Collection
                                            </a>
                                        </li>
                                        <li>
                                            <a href="https://github.com/quinnandrews/spring-docusaurus-jaxrs"
                                               target="_blank">
                                                GitHub Repository
                                                <svg width="13.5"
                                                     height="13.5"
                                                     aria-hidden="true"
                                                     viewBox="0 0 24 24" style={{marginLeft: "0.3rem"}}>
                                                    <path fill="currentColor"
                                                          d="M21 13v10h-21v-19h12v2h-10v15h17v-8h2zm3-12h-10.988l4.035 4-6.977 7.07 2.828 2.828 6.977-7.07 4.125 4.172v-11z"></path>
                                                </svg>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="https://spring.io/"
                                               target="_blank">
                                                Spring
                                                <svg width="13.5"
                                                     height="13.5"
                                                     aria-hidden="true"
                                                     viewBox="0 0 24 24" style={{marginLeft: "0.3rem"}}>
                                                    <path fill="currentColor"
                                                          d="M21 13v10h-21v-19h12v2h-10v15h17v-8h2zm3-12h-10.988l4.035 4-6.977 7.07 2.828 2.828 6.977-7.07 4.125 4.172v-11z"></path>
                                                </svg>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="https://docusaurus.io/"
                                               target="_blank">
                                                Docusaurus
                                                <svg width="13.5"
                                                     height="13.5"
                                                     aria-hidden="true"
                                                     viewBox="0 0 24 24" style={{marginLeft: "0.3rem"}}>
                                                    <path fill="currentColor"
                                                          d="M21 13v10h-21v-19h12v2h-10v15h17v-8h2zm3-12h-10.988l4.035 4-6.977 7.07 2.828 2.828 6.977-7.07 4.125 4.172v-11z"></path>
                                                </svg>
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </main>
        </>
    );
}

export default function Home() {
    return (
        <Layout
            title={`Home`}>
            <ApplicationDetailsDashboard/>
            {/*<main>*/}
            {/*    <HomepageFeatures/>*/}
            {/*</main>*/}
        </Layout>
    );
}
