// @ts-check
// `@type` JSDoc annotations allow editor autocompletion and type checking
// (when paired with `@ts-check`).
// There are various equivalent ways to declare your Docusaurus config.
// See: https://docusaurus.io/docs/api/docusaurus-config

import {themes as prismThemes} from 'prism-react-renderer';

/** @type {import('@docusaurus/types').Config} */
const config = {

  title: 'Spring + Docusaurus',
  tagline: 'Dinosaurs are cool',
  favicon: 'img/favicon.ico',

  // Set the production url of your site here
  url: 'https://your-docusaurus-site.example.com',
  // Set the /<baseUrl>/ pathname under which your site is served
  // For GitHub pages deployment, it is often '/<projectName>/'
  baseUrl: '/example/developer/',

  // GitHub pages deployment config.
  // If you aren't using GitHub pages, you don't need these.
  // organizationName: 'facebook', // Usually your GitHub org/user name.
  // projectName: 'docusaurus', // Usually your repo name.

  onBrokenLinks: 'throw',
  onBrokenMarkdownLinks: 'warn',

  // Even if you don't use internationalization, you can use this field to set
  // useful metadata like html lang. For example, if your site is Chinese, you
  // may want to replace "en" with "zh-Hans".
  i18n: {
    defaultLocale: 'en',
    locales: ['en'],
  },

  customFields: {
    // API Endpoint from which Docusaurus can get information about its host, the
    // associated Spring Boot Application. Used to surface data relevant to dev teams
    // on the Docusaurus home page as a type of dashboard, data such as the current
    // application version, when it was built, how long it's been running, etc.
    applicationDetailsPath: "/example/rest/developer/application/details",
  },

  presets: [
    [
      'classic',
      /** @type {import('@docusaurus/preset-classic').Options} */
      ({
        docs: {
          sidebarPath: './sidebars.js',
        },
        blog: {
          showReadingTime: true,
        },
        theme: {
          customCss: './src/css/custom.css',
        },
      }),
    ],
  ],

  themeConfig:
    /** @type {import('@docusaurus/preset-classic').ThemeConfig} */
    ({
      // Replace with your project's social card
      //image: 'img/docusaurus-social-card.jpg',
      navbar: {
        title: 'Spring + Docusaurus',
        logo: {
          alt: 'My Site Logo',
          src: 'img/logo.svg',
        },
        items: [
          {
            type: 'dropdown',
            label: 'API Docs',
            position: 'left',
            items: [
              {
                label: 'Public REST API',
                href: 'docs/api/rest/public',
                prependBaseUrlToHref: true,
                target: '_blank'
              },
              {
                label: 'Restricted REST API',
                href: 'docs/api/rest/restricted',
                prependBaseUrlToHref: true,
                target: '_blank'
              }
            ],
          },
          {
            type: 'dropdown',
            label: 'Tech Docs',
            position: 'left',
            items: [
              {
                to: '/docs/category/adrs',
                label: 'ADRs',
              },
              {
                to: '/docs/category/readmes',
                label: 'READMEs',
              },
            ],
          },
          {
            to: '/blog',
            label: 'Blog',
            position: 'left'
          },

          // {
          //   href: 'https://github.com/facebook/docusaurus',
          //   label: 'GitHub',
          //   position: 'right',
          // },
          {
            type: 'search',
            position: 'right',
          },
        ],
      },
      footer: {
        style: 'dark',
        copyright: `Copyright © ${new Date().getFullYear()} Quinn Andrews. Built with Docusaurus... and Spring!`,
      },
      prism: {
        theme: prismThemes.github,
        darkTheme: prismThemes.dracula,
      },
    }),
};

export default config;
